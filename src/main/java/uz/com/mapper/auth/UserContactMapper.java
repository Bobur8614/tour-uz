package uz.com.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.UserContactCreateDto;
import uz.com.dto.auth.UserContactDto;
import uz.com.dto.auth.UserContactUpdateDto;
import uz.com.hibernate.domain.auth.UserContact;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface UserContactMapper extends BaseMapper<UserContact, UserContactDto, UserContactCreateDto, UserContactUpdateDto> {

    @Override
    UserContactDto toDto(UserContact entity);

    @Override
    UserContact fromCreateDto(UserContactCreateDto createDto);
}
