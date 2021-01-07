package uz.com.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContactUpdateDto extends GenericCrudDto {

    private Long id;

    private String email;

    private String phoneNumber;

    private Long userId;

}
