package uz.com.controller.activity;


import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.com.controller.ApiController;
import uz.com.dto.activity.ActivityDto;
import uz.com.response.DataDto;
import uz.com.service.settings.IActivityService;

import java.util.List;

@Api(value = "Activity controller")
@RestController
public class ActivityController extends ApiController<IActivityService> {
    public ActivityController(IActivityService service) {
        super(service);
    }

    @RequestMapping(value = API_PATH + V_1 + "/activity", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<ActivityDto>>> getDetails() {
        return service.getAllMyActivities();
    }

}
