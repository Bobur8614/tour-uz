package uz.com.service.settings;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.settings.ActivityCriteria;
import uz.com.dto.activity.ActivityDto;
import uz.com.hibernate.domain.activity.Activity;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import java.util.List;
import java.util.Map;

public interface IActivityService extends IAbstractService {

    ResponseEntity<DataDto<List<ActivityDto>>> getAllMyActivities();

    Activity saveRole(Map<String, String> map);


}
