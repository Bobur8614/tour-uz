package uz.com.dto.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Resource file update request")
public class ResourceFileUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    private String name;

}
