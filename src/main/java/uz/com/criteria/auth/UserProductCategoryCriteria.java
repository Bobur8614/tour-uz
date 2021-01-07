package uz.com.criteria.auth;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProductCategoryCriteria extends GenericCriteria {

    private Long userId;
    private Long productCategoryId;
    private Long responsibleTypeId;

    @Builder(builderMethodName = "childBuilder")
    public UserProductCategoryCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, Long userId, Long productCategoryId, Long responsibleTypeId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.userId = userId;
        this.productCategoryId = productCategoryId;
        this.responsibleTypeId = responsibleTypeId;
    }
}
