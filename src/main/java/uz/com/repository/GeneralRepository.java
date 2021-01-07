package uz.com.repository;

import org.springframework.stereotype.Repository;
import uz.com.criteria.GenericCriteria;
import uz.com.dto.auth.AttachPermissionRoleDto;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.dao.FunctionParam;
import uz.com.hibernate.dao.GenericDao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GeneralRepository extends GenericDao<_Entity, GenericCriteria> implements IGeneralRepository {

    @Override
    public Boolean attachRole(AttachPermissionRoleDto dto) {
        return call(dto, "attachPermissionRole", Types.BOOLEAN);
    }

    @Override
    public String hasRoleExistPermission(Long permissionId) {
        List<FunctionParam> params = new ArrayList<>();
        params.add(new FunctionParam(permissionId, java.sql.Types.BIGINT));
        params.add(new FunctionParam(userSession.getUser().getId(), java.sql.Types.BIGINT));
        return call(params, "hasRoleExistPermission", Types.VARCHAR);
    }

}
