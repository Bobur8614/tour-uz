package uz.com.dto.auth;

import lombok.*;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.GenericDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeSupplyDto extends GenericCrudDto {

    private Long userId;
    private Long supplyDepartmentTypeId;

    private List<ProductCategoryResponsibleDto> categoryResponsibleDtos;

}
