package uz.com.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.auth.PermissionCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.response.DataDto;
import uz.com.service.auth.IPermissionService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Permission controller")
@RestController
public class PermissionController extends ApiController<IPermissionService> {
    public PermissionController(IPermissionService service) {
        super(service);
    }

    @ApiOperation(value = "Get permission")
    @RequestMapping(value = API_PATH + V_1 + "/permissions/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<PermissionDto>> getPermission(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get all permission")
    @RequestMapping(value = API_PATH + V_1 + "/permissions", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PermissionDto>>> getAllPermissions(@Valid PermissionCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Get all permissions to access")
    @RequestMapping(value = API_PATH + V_1 + "/permissions/access", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PermissionAccessDto>>> getAllPermissionsToAccess(@Valid PermissionCriteria criteria) {
        return service.getAllToAccess(criteria);
    }

    @ApiOperation(value = "Create permission")
    @RequestMapping(value = API_PATH + V_1 + "/permissions/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createPermission(@RequestBody PermissionCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Update permission")
    @RequestMapping(value = API_PATH + V_1 + "/permissions/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<PermissionDto>> updatePermission(@RequestBody PermissionUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Delete permission")
    @RequestMapping(value = API_PATH + V_1 + "/permissions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deletePermission(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }
}
