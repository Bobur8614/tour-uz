package uz.com.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContactCreateDto extends GenericCrudDto {

    private String email;

    private String phoneNumber;

    @ApiModelProperty(hidden = true)
    private Long userId;

}
