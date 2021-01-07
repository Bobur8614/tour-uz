package uz.com.mapper.activity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.activity.ActivityCreateDto;
import uz.com.dto.activity.ActivityDto;
import uz.com.dto.activity.ActivityUpdateDto;
import uz.com.hibernate.domain.activity.Activity;
import uz.com.mapper.BaseMapper;
import uz.com.mapper.auth.PermissionMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {PermissionMapper.class})
@Component
public interface ActivityMapper extends BaseMapper<Activity, ActivityDto, ActivityCreateDto, ActivityUpdateDto> {

    @Override
    @Mapping(target = "createdDate", source = "auditInfo.createdDate", dateFormat = "dd.MM.yyyy")
    ActivityDto toDto(Activity entity);

}
