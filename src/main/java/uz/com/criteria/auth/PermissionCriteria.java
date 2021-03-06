package uz.com.criteria.auth;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCriteria extends GenericCriteria {

    private String code;

    private String name;

    @Builder(builderMethodName = "childBuilder")
    public PermissionCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String code, String name) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.code = code;
        this.name = name;
    }
}
