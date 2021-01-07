package uz.com.validator.file;


import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.file.ResourceFileCreateDto;
import uz.com.dto.file.ResourceFileUpdateDto;
import uz.com.enums.ErrorCodes;
import uz.com.exception.IdRequiredException;
import uz.com.exception.RequestObjectNullPointerException;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.utils.BaseUtils;
import uz.com.validator.BaseCrudValidator;


@Component
public class ResourceFileValidator extends BaseCrudValidator<ResourceFile, ResourceFileCreateDto, ResourceFileUpdateDto> {
    public ResourceFileValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }

    @Override
    public void baseValidation(ResourceFile domain, boolean idRequired) {
        if (utils.isEmpty(domain)) {
            throw new RequestObjectNullPointerException(dao.getErrorMessage(ErrorCodes.OBJECT_IS_NULL, utils.toErrorParams(ResourceFile.class)), "ResourceFile");
        } else if (idRequired && utils.isEmpty(domain.getId())) {
            throw new IdRequiredException(dao.getErrorMessage(ErrorCodes.ID_REQUIRED, ""), "id");
        }/* else if (utils.isEmpty(domain.getObjectType())) {
            throw new ValidationException(repository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("objectType", ResourceFile.class)), "objectType");
        } else if (utils.isEmpty(domain.getObjectId())) {
            throw new ValidationException(repository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("objectId", ResourceFile.class)), "objectId");
        } else if (utils.isEmpty(domain.getUrl())) {
            throw new ValidationException(dao.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("url", ResourceFile.class)), "url");
        }*/
    }
}
