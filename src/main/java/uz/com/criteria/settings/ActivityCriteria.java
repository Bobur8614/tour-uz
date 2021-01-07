package uz.com.criteria.settings;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCriteria extends GenericCriteria {
    private Long userId;

    @Builder(builderMethodName = "childBuilder")
    public ActivityCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, Long userId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.userId = userId;
    }
}
