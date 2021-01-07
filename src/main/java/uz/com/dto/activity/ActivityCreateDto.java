package uz.com.dto.activity;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@ApiModel(value = "Activity create request")
public class ActivityCreateDto extends GenericCrudDto {
}
