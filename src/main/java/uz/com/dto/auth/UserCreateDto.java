package uz.com.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.GenericDto;
import uz.com.enums.GenderType;
import uz.com.hibernate.domain.auth.UserContact;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.hibernate.domain.settings.Language;
import uz.com.hibernate.domain.settings.Type;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.function.LongToDoubleFunction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User create request")
public class UserCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String firstName;

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String lastName;

    @Size(max = 100, message = " max size %s")
    private String middleName;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(example = "20-02-2020 00:00:00") /*dd-MM-yyyy HH:mm:ss*/
    private String birthDate;

    @ApiModelProperty(required = false)
    private Long ordering;

    @ApiModelProperty(required = false)
    private Long supplyDepartmentTypeId;

    @ApiModelProperty(required = false)
    private Long positionTypeId;

    @ApiModelProperty(required = false)
    private Long resourceFileId;

    @ApiModelProperty(required = false)
    private Boolean gender;

    private List<UserContactCreateDto> contacts;
    private List<GenericDto> organizations;
    private List<GenericDto> roles;


}
