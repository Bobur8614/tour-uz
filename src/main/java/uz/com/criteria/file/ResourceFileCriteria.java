package uz.com.criteria.file;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFileCriteria extends GenericCriteria {
    private String name;
    private String mimeType;
    private Long size;
    private boolean isDefault;
    private Long objectId;

    @Builder(builderMethodName = "childBuilder")
    public ResourceFileCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String name, String mimeType, Long size, boolean isDefault, Long objectId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.name = name;
        this.mimeType = mimeType;
        this.size = size;
        this.isDefault = isDefault;
        this.objectId = objectId;
    }
}
