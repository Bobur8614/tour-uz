package uz.com.service.auth;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.com.criteria.auth.UserContactCriteria;
import uz.com.criteria.auth.UserCriteria;
import uz.com.criteria.auth.UserProductCategoryCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.enums.GenderType;
import uz.com.enums.State;
import uz.com.hibernate.dao.auth.RoleDao;
import uz.com.hibernate.dao.auth.UserContactDao;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.hibernate.dao.file.ResourceFileDao;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.dao.settings.LanguageDao;
import uz.com.hibernate.dao.settings.TypeDao;
import uz.com.hibernate.domain.auth.Role;
import uz.com.hibernate.domain.auth.User;
import uz.com.hibernate.domain.auth.UserContact;
import uz.com.hibernate.domain.settings.Type;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.auth.UserContactMapper;
import uz.com.mapper.auth.UserMapper;
import uz.com.repository.IGeneralRepository;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.utils.UserSession;
import uz.com.validator.auth.UserServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserService implements IUserService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserSession userSession;
    private final UserMapper userMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final UserServiceValidator validator;
    private final LanguageDao languageDao;
    private final ErrorMessageDao errorMessageDao;
    private final IGeneralRepository generalRepository;
    private final TypeDao typeDao;
    private final ResourceFileDao resourceFileDao;
//    private final OrganizationDao organizationDao;
    private final UserContactDao userContactDao;
    private final UserContactMapper userContactMapper;


    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, UserSession userSession, UserMapper userMapper, GenericMapper genericMapper, BaseUtils utils, UserServiceValidator validator, LanguageDao languageDao, ErrorMessageDao errorMessageDao, IGeneralRepository generalRepository, TypeDao typeDao, ResourceFileDao resourceFileDao, UserContactDao userContactDao, UserContactMapper userContactMapper) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userSession = userSession;
        this.userMapper = userMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
        this.languageDao = languageDao;
        this.errorMessageDao = errorMessageDao;
        this.generalRepository = generalRepository;
        this.typeDao = typeDao;
        this.resourceFileDao = resourceFileDao;
        this.userContactDao = userContactDao;
        this.userContactMapper = userContactMapper;
    }


    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<UserDetailsDto>> get(Long id) {
        User user = userDao.get(id);
        validator.validateUserActive(user, id);
        if (utils.isEmpty(user)) {
            logger.error(String.format("User with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == user.getState()) {
            logger.error(String.format("User with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (user.isLocked()) {
            logger.error(String.format("User with id '%s' is locked", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User with id '%s' is locked", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(userMapper.toDetailsDto(user)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<List<UserDto>>> getAll(UserCriteria criteria) {
        if (criteria.isMyUsers() && !userSession.getAllInfo()) {
            criteria.setMyUsers(true);
        } else {
            criteria.setMyUsers(false);
        }
        Long total = userDao.total(criteria);
        List<User> list = userDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(userMapper.toDto(list), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull UserCreateDto dto) {
        User user = userMapper.fromCreateDto(dto);
        if (utils.isEmpty(dto.getFirstName()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("First name should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getLastName()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Last name should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getPositionTypeId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Position type id should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getPassword()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Password should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getUsername()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Username should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getGender()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Gender should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getContacts()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Contacts should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getOrganizations()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Organization should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(userDao.findByUsername(dto.getUsername())))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User with this username %s already exist", dto.getUsername())).build()), HttpStatus.CONFLICT);

        if (!utils.isEmpty(dto.getSupplyDepartmentTypeId())) {
            user.setSupplyDepartmentType(typeDao.get(dto.getSupplyDepartmentTypeId()));
        }
        if (!utils.isEmpty(dto.getResourceFileId())) {
            user.setResourceFile(resourceFileDao.get(dto.getResourceFileId()));
        }
        if (dto.getGender()) {
            user.setGenderType(GenderType.Male);
        } else {
            user.setGenderType(GenderType.Female);
        }
//        List<Organization> organizations = new ArrayList<>();
//        dto.getOrganizations().forEach(organization -> {
//            organizations.add(organizationDao.get(organization.getId()));
//        });
//        user.setBirthDate(utils.toLocalDate(dto.getBirthDate()));
//        user.setOrganizations(organizations);
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setPositionType(typeDao.get(dto.getPositionTypeId()));
        List<Role> roles = new ArrayList<>();
        dto.getRoles().forEach(role -> {
            roles.add(roleDao.get(role.getId()));
        });
        user.setRoles(roles);
        user.setLanguage(languageDao.findByCode("ru"));
        User newUser = userDao.save(user);
        dto.getContacts().forEach(contact -> {
            UserContact userContact = userContactMapper.fromCreateDto(contact);
            userContact.setUser(newUser);
            userContactDao.save(userContact);
        });
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(user)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_UPDATE)")
    public ResponseEntity<DataDto<UserDetailsDto>> update(@NotNull UserUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("User id should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getFirstName()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("First name should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getLastName()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Last name should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getPassword()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Password should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getUsername()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Username should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getGender()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Gender should not null").build()), HttpStatus.BAD_REQUEST);
        if (utils.isEmpty(dto.getUserContacts()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Contacts should not null").build()), HttpStatus.BAD_REQUEST);

        User user = (User) utils.parseJson(userDao.get(dto.getId()), new Gson().toJson(dto));
        if (!utils.isEmpty(userDao.findByUserNameNotId(dto.getUsername(), user.getId())))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User with this username %s already exist", dto.getUsername())).build()), HttpStatus.CONFLICT);

        if (!utils.isEmpty(dto.getSupplyDepartmentTypeId())) {
            user.setSupplyDepartmentType(typeDao.get(dto.getSupplyDepartmentTypeId()));
        } else {
            user.setSupplyDepartmentType(null);
        }
        if (!utils.isEmpty(dto.getResourceFileId())) {
            user.setResourceFile(resourceFileDao.get(dto.getResourceFileId()));
        }
        if (dto.getGender()) {
            user.setGenderType(GenderType.Male);
        } else {
            user.setGenderType(GenderType.Female);
        }
//        List<Organization> organizations = new ArrayList<>();
//        dto.getOrganizations().forEach(organization -> {
//            organizations.add(organizationDao.get(organization.getId()));
//        });
//        user.setOrganizations(organizations);
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setPositionType(typeDao.get(dto.getPositionTypeId()));
        List<Role> roles = new ArrayList<>();
        dto.getRoles().forEach(role -> {
            roles.add(roleDao.get(role.getId()));
        });
        user.setRoles(roles);
        User newUser = userDao.save(user);
        List<UserContact> userContacts = userContactDao.list(UserContactCriteria.childBuilder().user(userMapper.toDto(newUser)).build());
        userContacts.forEach(userContact -> {
            userContactDao.delete(userContact.getId());
        });
        dto.getUserContacts().forEach(contact -> {
            UserContact userContact = userContactMapper.fromCreateDto(contact);
            userContact.setUser(newUser);
            userContactDao.save(userContact);
        });
        return get(user.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        User user = userDao.get(id);
        if (user == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == user.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("User already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        userDao.delete(user);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_ATTACH_ROLE)")
    public ResponseEntity<DataDto<UserDetailsDto>> attachRolesToUser(@NotNull AttachRoleDto dto) {
        validator.validateOnAttach(dto);
        User user = userDao.get(dto.getUserId());
        validator.validateUserActive(user, dto.getUserId());
        List<Role> roles = new ArrayList<>();
        dto.getRoles().forEach(genericDto -> {
            Role role = roleDao.get(genericDto.getId());
            if (role == null)
                throw new RuntimeException(String.format("Role with id '%s' not found", genericDto.getId()));
            if (State.DELETED == role.getState())
                throw new RuntimeException(String.format("Role with id '%s' is deleted", genericDto.getId()));
            roles.add(role);
        });
        user.setRoles(roles);
        userDao.save(user);
        return get(user.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_CHANGE_PASSWORD)")
    public ResponseEntity<DataDto<Boolean>> changePassword(@NotNull ChangePasswordDto dto) {
        validator.validateChangePassword(dto);
        User user = userDao.get(dto.getUserId());
        validator.validateUserActive(user, dto.getUserId());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        userDao.save(user);
        return ResponseEntity.ok(new DataDto<>(true));
    }

    @Override
    public ResponseEntity<DataDto<UserDto>> getDetails() {
        User user = userDao.get(userSession.getUser().getId());
        UserDto dto = userMapper.toDto(user);
        return new ResponseEntity<>(new DataDto<>(dto), HttpStatus.OK);
    }


}
