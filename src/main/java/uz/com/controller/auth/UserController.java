package uz.com.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.auth.UserCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.response.DataDto;
import uz.com.service.auth.IUserService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "User controller", description = "Users")
@RestController
public class UserController extends ApiController<IUserService> {
    public UserController(IUserService service) {
        super(service);
    }

    @ApiOperation(value = "Get user")
    @RequestMapping(value = API_PATH + V_1 + "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<UserDetailsDto>> getUser(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get all users")
    @RequestMapping(value = API_PATH + V_1 + "/users", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<UserDto>>> getAllUsers(@Valid UserCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Create user")
    @RequestMapping(value = API_PATH + V_1 + "/user/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createUser(@RequestBody UserCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Update user")
    @RequestMapping(value = API_PATH + V_1 + "/user/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<UserDetailsDto>> updateUser(@RequestBody UserUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Delete user")
    @RequestMapping(value = API_PATH + V_1 + "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteUser(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "User attach role")
    @RequestMapping(value = API_PATH + V_1 + "/users/attach/role", method = RequestMethod.POST)
    public ResponseEntity<DataDto<UserDetailsDto>> attachRoles(@RequestBody AttachRoleDto dto) {
        return service.attachRolesToUser(dto);
    }

    @ApiOperation(value = "User change password")
    @RequestMapping(value = API_PATH + V_1 + "/users/change/password", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> changePassword(@RequestBody ChangePasswordDto dto) {
        return service.changePassword(dto);
    }

    @ApiOperation(value = "Get Session user")
    @RequestMapping(value = API_PATH + V_1 + "/users/me", method = RequestMethod.GET)
    public ResponseEntity<DataDto<UserDto>> getDetails(){
        return service.getDetails();
    }
    
}
