package uz.com.service.file;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.file.ResourceFileCriteria;
import uz.com.dto.file.ResourceFileCreateDto;
import uz.com.dto.file.ResourceFileDto;
import uz.com.dto.file.ResourceFileUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IResourceFileService extends IAbstractService {

    ResponseEntity<DataDto<ResourceFileDto>> get(Long id);

    ResponseEntity<DataDto<List<ResourceFileDto>>> getAll(ResourceFileCriteria criteria);

    ResourceFileDto createFile(ResourceFileCreateDto dto);

    ResourceFileDto getOne(Long id);


    ResponseEntity<DataDto<ResourceFileDto>> update(@NotNull ResourceFileUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
