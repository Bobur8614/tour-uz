package uz.com.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.GenericDto;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User update request")
public class UserUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @Size(max = 100, message = " max size %s")
    private String firstName;

    @Size(max = 100, message = " max size %s")
    private String lastName;

    @Size(max = 100, message = " max size %s")
    private String middleName;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(example = "20-02-2020") /*dd-MM-yyyy HH:mm:ss*/
    private String birthDate;

    @ApiModelProperty(required = true)
    private Long ordering;

    @ApiModelProperty(required = false)
    private Long supplyDepartmentTypeId;

    @ApiModelProperty(required = false)
    private Long positionTypeId;

    @ApiModelProperty(required = false)
    private Long resourceFileId;

    @ApiModelProperty(required = false)
    private Boolean gender;

    private List<UserContactCreateDto> userContacts;
    private List<GenericDto> organizations;
    private List<GenericDto> roles;

}
