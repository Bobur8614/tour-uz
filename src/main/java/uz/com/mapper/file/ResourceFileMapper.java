package uz.com.mapper.file;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.file.ResourceFileCreateDto;
import uz.com.dto.file.ResourceFileDto;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface ResourceFileMapper extends BaseMapper<ResourceFile, ResourceFileDto, ResourceFileCreateDto, GenericCrudDto> {
    ResourceFileDto fromCreateDtoToDto(ResourceFileCreateDto dto);
}
