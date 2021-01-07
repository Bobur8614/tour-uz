package uz.com.controller.settings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.TypeCreateDto;
import uz.com.dto.settings.TypeDto;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.settings.ITypeService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Type controller", description = "Settings Type")
@RestController
public class TypeController extends ApiController<ITypeService> {
    public TypeController(ITypeService service) {
        super(service);
    }

    @ApiOperation(value = "Get type")
    @RequestMapping(value = API_PATH + V_1 + "/types/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<TypeDto>> getType(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get all types")
    @RequestMapping(value = API_PATH + V_1 + "/types", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<TypeDto>>> getAllTypes(@Valid TypeCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Get all parent types")
    @RequestMapping(value = API_PATH + V_1 + "/types/parents", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<TypeDto>>> getAllParentTypes(@Valid TypeCriteria criteria) {
        criteria.setParent(true);
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Create type")
    @RequestMapping(value = API_PATH + V_1 + "/types/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createType(@RequestBody TypeCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Update type")
    @RequestMapping(value = API_PATH + V_1 + "/types/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<TypeDto>> updateType(@RequestBody TypeUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Delete type")
    @RequestMapping(value = API_PATH + V_1 + "/types/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteType(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

}
