package uz.com.criteria.auth;

import lombok.*;
import uz.com.criteria.GenericCriteria;
import uz.com.dto.auth.UserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContactCriteria extends GenericCriteria {

    private String email;
    private String typeCode;
    private String phoneNumber;
    private UserDto user;

    @Builder(builderMethodName = "childBuilder")
    public UserContactCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String email, String typeCode, String phoneNumber, UserDto user) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.email = email;
        this.typeCode = typeCode;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }
}
