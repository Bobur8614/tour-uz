package uz.com.dto.auth;

import lombok.*;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContactDto extends GenericDto {

    private String email;

    private String phoneNumber;

    private Long userId;

    @Builder(builderMethodName = "childBuilder")
    public UserContactDto(Long id, String email, String phoneNumber, Long userId) {
        super(id);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }
}
