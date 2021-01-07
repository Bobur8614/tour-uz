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
@ApiModel(value = "Resource file create request")
public class ResourceFileCreateDto extends GenericCrudDto {
    private String name;

    @ApiModelProperty(required = true)
    private String url;

    private String mimeType;
    private Long size;
    private boolean isDefault;


}
