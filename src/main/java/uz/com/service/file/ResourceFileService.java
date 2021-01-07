package uz.com.service.file;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.file.ResourceFileCriteria;
import uz.com.dto.file.ResourceFileCreateDto;
import uz.com.dto.file.ResourceFileDto;
import uz.com.dto.file.ResourceFileUpdateDto;
import uz.com.enums.State;
import uz.com.hibernate.dao.file.ResourceFileDao;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.file.ResourceFileMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.file.ResourceFileValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "resourceFileService")
public class ResourceFileService implements IResourceFileService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final ResourceFileDao resourceFileDao;
    private final ResourceFileMapper resourceFileMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final ResourceFileValidator validator;

    @Autowired
    public ResourceFileService(ResourceFileDao resourceFileDao, ResourceFileMapper resourceFileMapper, GenericMapper genericMapper, BaseUtils utils, ResourceFileValidator validator) {
        this.resourceFileDao = resourceFileDao;
        this.resourceFileMapper = resourceFileMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PRODUCT_READ)")
    public ResponseEntity<DataDto<ResourceFileDto>> get(Long id) {
        ResourceFile resourceFile = resourceFileDao.get(id);
        if (utils.isEmpty(resourceFile)) {
            logger.error(String.format("ResourceFile with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("ResourceFile with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == resourceFile.getState()) {
            logger.error(String.format("ResourceFile with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("ResourceFile with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(resourceFileMapper.toDto(resourceFile)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PRODUCT_READ)")
    public ResponseEntity<DataDto<List<ResourceFileDto>>> getAll(ResourceFileCriteria criteria) {
        Long total = resourceFileDao.total(criteria);
        Stream<ResourceFile> stream = resourceFileDao.listStream(criteria);
        return new ResponseEntity<>(new DataDto<>(resourceFileMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PRODUCT_CREATE)")
    public ResourceFileDto createFile(ResourceFileCreateDto dto) {
        ResourceFile file = resourceFileMapper.fromCreateDto(dto);
        validator.validateDomainOnCreate(file);
        resourceFileDao.save(file);
        return resourceFileMapper.toDto(resourceFileDao.get(file.getId()));
    }

    @Override
    public ResourceFileDto getOne(Long id) {
        return resourceFileMapper.toDto(resourceFileDao.get(id));
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PRODUCT_UPDATE)")
    public ResponseEntity<DataDto<ResourceFileDto>> update(@NotNull ResourceFileUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Resource File id should not be null !!!").build()), HttpStatus.BAD_REQUEST);
        ResourceFile resourceFile = (ResourceFile) utils.parseJson(resourceFileDao.get(dto.getId()), new Gson().toJson(dto));
        resourceFileDao.save(resourceFile);
        return get(resourceFile.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PRODUCT_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        ResourceFile file = resourceFileDao.get(id);
        if (file == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Resource File not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == file.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Resource File already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        resourceFileDao.delete(file);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
