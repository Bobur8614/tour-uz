package uz.com.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.UserCreateDto;
import uz.com.dto.auth.UserDetailsDto;
import uz.com.dto.auth.UserDto;
import uz.com.dto.auth.UserUpdateDto;
import uz.com.hibernate.domain.auth.User;
import uz.com.mapper.BaseMapper;
import uz.com.utils.StringToLocalDate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RoleMapper.class, StringToLocalDate.class, UserContactMapper.class})
@Component
public interface UserMapper extends BaseMapper<User, UserDto, UserCreateDto, UserUpdateDto> {

    @Override
    @Mapping(target = "langCode", source = "language.code")
    @Mapping(target = "supplyDepartmentTypeName", source = "supplyDepartmentType.name")
    @Mapping(target = "positionTypeName", source = "positionType.name")
    @Mapping(target = "photoUrl", source = "resourceFile.url")
    @Mapping(target = "roles", ignore = true)
    UserDto toDto(User entity);

    @Mapping(target = "langCode", source = "language.code")
    @Mapping(target = "photoUrl", source = "resourceFile.url")
    @Mapping(target = "roles", ignore = true)
    UserDetailsDto toDetailsDto(User entity);


    @Override
    @Mapping(target = "birthDate", ignore = true)
    User fromCreateDto(UserCreateDto createDto);
}
