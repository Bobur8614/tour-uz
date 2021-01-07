package uz.com.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.enums.State;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends GenericDto {

    private String firstName;
    private String lastName;
    private String middleName;
    private String shortName;
    private String username;
    private String birthDate;
    private Long ordering;
    private Boolean isOnline;
    private Long chatId;

    private String supplyDepartmentTypeName;
    private String positionTypeName;
    private String langCode;
    private String photoUrl;
    private List<UserContactDto> contacts;
//    private List<CompaDto> organizations;


    private State state;
    private List<RoleDto> roles;
    @ApiModelProperty(hidden = true)
    private boolean locked;
    @ApiModelProperty(hidden = true)
    private boolean systemAdmin;

    @Builder(builderMethodName = "childBuilder")
    public UserDto(Long id, String firstName, String lastName, String middleName, String shortName, String username, String birthDate, Long ordering, Boolean isOnline, Long chatId, String supplyDepartmentTypeName, String positionTypeName, String langCode, String photoUrl, List<UserContactDto> contacts, State state, List<RoleDto> roles, boolean locked, boolean systemAdmin) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.username = username;
        this.birthDate = birthDate;
        this.ordering = ordering;
        this.isOnline = isOnline;
        this.chatId = chatId;
        this.supplyDepartmentTypeName = supplyDepartmentTypeName;
        this.positionTypeName = positionTypeName;
        this.langCode = langCode;
        this.photoUrl = photoUrl;
        this.contacts = contacts;
        this.state = state;
        this.roles = roles;
        this.locked = locked;
        this.systemAdmin = systemAdmin;
    }
}
