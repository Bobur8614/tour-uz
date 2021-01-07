package uz.com.service.settings;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import uz.com.criteria.settings.ActivityCriteria;
import uz.com.dto.activity.ActivityDto;
import uz.com.hibernate.dao.auth.RoleDao;
import uz.com.hibernate.dao.settings.ActivityDao;
import uz.com.hibernate.domain.activity.Activity;
import uz.com.hibernate.domain.auth.Role;
import uz.com.mapper.activity.ActivityMapper;
import uz.com.response.DataDto;
import uz.com.utils.UserSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "activeService")
public class ActivityService implements IActivityService {


    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    RoleDao roleDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    private UserSession userSession;

    @Override
    public ResponseEntity<DataDto<List<ActivityDto>>> getAllMyActivities() {
        ActivityCriteria criteria = ActivityCriteria.childBuilder().userId(userSession.getUser().getId()).build();
        Long total = activityDao.total(criteria);
        Stream<Activity> stream = activityDao.listStream(criteria);
        return new ResponseEntity<>(new DataDto<>(activityMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
    public Activity saveRole(Map<String, String> map) {
        Role role = roleDao.get(Long.parseLong(map.get("objectId")));
        HashMap values = new HashMap();
        values.put("role", role);
        values.put("createdAt", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
        Activity activity = new Activity();
        String descriptionEn = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template/role/role_create_en.vm", values);
        String descriptionRu = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template/role/role_create_ru.vm", values);
        String descriptionUz = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template/role/role_create_uz.vm", values);
        activity.setObjectId(role.getId());
        activity.setObjectName("role");
        activity.setDescriptionEn(descriptionEn);
        activity.setDescriptionRu(descriptionRu);
        activity.setDescriptionUz(descriptionUz);
        activityDao.save(activity);
        return activity;
    }
}
