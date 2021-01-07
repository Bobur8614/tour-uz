package uz.com.service.file;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.com.criteria.file.ResourceFileCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.file.ResourceFileCreateDto;
import uz.com.dto.file.ResourceFileDto;
import uz.com.dto.file.ResourceFileUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IFileStorageService {

    void init() throws Exception;

    ResponseEntity<DataDto<ResourceFileDto>> storeFile(MultipartFile file, Integer minWidth, Integer minHeight);

    ResponseEntity<DataDto<List<ResourceFileDto>>> storeFiles(List<MultipartFile> multipartFileList, Integer minWidth, Integer minHeight);

    Resource loadFileAsResource(String fileName);

    void delete(String fileName);

    void deleteAll();

    ResponseEntity<DataDto<Boolean>> delete(Long id);
}
