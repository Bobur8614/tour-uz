package uz.com.dto.auth;

import lombok.*;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponsibleDto extends GenericCrudDto {

    private Long productCategoryId;
    private Long responsibleTypeId;

}
