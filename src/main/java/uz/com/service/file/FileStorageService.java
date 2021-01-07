package uz.com.service.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.com.dto.file.ResourceFileCreateDto;
import uz.com.dto.file.ResourceFileDto;
import uz.com.enums.ErrorCodes;
import uz.com.exception.GenericRuntimeException;
import uz.com.exception.RequestObjectNullPointerException;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.property.FileStorageProperties;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.utils.ImageUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageService implements IFileStorageService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());

    private final BaseUtils baseUtils;
    private final ErrorMessageDao errorMessageDao;
    private final IResourceFileService resourceFileService;
    private final Path rootLocation;
    private final ImageUtils imageUtils;

    @Autowired
    public FileStorageService(BaseUtils baseUtils, ErrorMessageDao errorMessageDao, IResourceFileService resourceFileService, FileStorageProperties properties, ImageUtils imageUtils) {
        this.baseUtils = baseUtils;
        this.errorMessageDao = errorMessageDao;
        this.resourceFileService = resourceFileService;
        this.rootLocation = Paths.get(properties.getUploadDir());
        this.imageUtils = imageUtils;
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Could not initialize dir", e);
        }
    }

    @Override
    public ResponseEntity<DataDto<ResourceFileDto>> storeFile(MultipartFile file, Integer minWidth, Integer minHeight) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (file.isEmpty()) {
            throw new RequestObjectNullPointerException(errorMessageDao.getErrorMessage(ErrorCodes.FAILED_STORE_ILLEGAL_CHARACTERS, baseUtils.toErrorParams(filename)));
        }
        if (filename.contains("..")) {
            throw new RequestObjectNullPointerException(errorMessageDao.getErrorMessage(ErrorCodes.INVALID_FILE_EXTENSION, baseUtils.toErrorParams(filename)));
        }

        ResourceFileDto resourceFileDto;

        String contentType = Objects.requireNonNull(file.getContentType());

        String fileNamePrefix = Objects.requireNonNull(StringUtils.split(filename, "."))[0];
        String fileExtension = StringUtils.getFilenameExtension(filename);
        String newFileName = baseUtils.encodeToMd5(fileNamePrefix) + new Date().getTime() + "." + fileExtension;

        if (contentType.startsWith("image") && !contentType.contains("svg+xml")) {
            try {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);

                if (!baseUtils.isEmpty(fileExtension) && (!"png".equals(fileExtension))) {
                    imageUtils.compressImage(this.rootLocation.resolve(newFileName).toString(),
                            this.rootLocation.resolve(this.rootLocation.resolve(newFileName)).toString(), minWidth, minHeight);
                }

                resourceFileDto = saveResourceFile(filename, newFileName, file);

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } else {
            try {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
                resourceFileDto = saveResourceFile(filename, newFileName, file);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        if (baseUtils.isEmpty(resourceFileDto)) {
            throw new RuntimeException(ErrorCodes.COULD_NOT_STORE_FILE.name());
        }

        return ResponseEntity.ok(new DataDto<>(resourceFileDto));
    }

    @Override
    public ResponseEntity<DataDto<List<ResourceFileDto>>> storeFiles(List<MultipartFile> multipartFileList, Integer minWidth, Integer minHeight) {
        List<ResourceFileDto> resourceFileDtoList = new ArrayList<>();
        multipartFileList.forEach(multipartFile -> resourceFileDtoList.add(Objects.requireNonNull(storeFile(multipartFile, minWidth, minHeight).getBody()).getData()));
        return ResponseEntity.ok(new DataDto<>(resourceFileDtoList));
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new GenericRuntimeException(errorMessageDao.getErrorMessage(ErrorCodes.FILE_NOT_FOUND, baseUtils.toErrorParams(fileName)));
            }
        } catch (Exception ex) {
            throw new GenericRuntimeException(errorMessageDao.getErrorMessage(ErrorCodes.FAILED_TO_STORE, baseUtils.toErrorParams(fileName)));
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.delete(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        ResourceFileDto resourceFileDto = resourceFileService.getOne(id);
        delete(resourceFileDto.getName());
        return resourceFileService.delete(id);
    }

    ResourceFileDto saveResourceFile(String fileName, String newFileName, MultipartFile file) {
        ResourceFileCreateDto fileCreateDto = new ResourceFileCreateDto();
        fileCreateDto.setName(fileName);
        fileCreateDto.setUrl("/api/v1/resource/uploads/" + newFileName);
        fileCreateDto.setMimeType(file.getContentType());
        fileCreateDto.setSize(file.getSize());

        return resourceFileService.createFile(fileCreateDto);
    }
}
