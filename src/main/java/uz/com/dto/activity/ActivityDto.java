package uz.com.dto.activity;

import lombok.*;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto extends GenericDto {

    private Long objectId;
    private String objectName;
    private String descriptionEn;
    private String descriptionRu;
    private String descriptionUz;
    private String createdDate;

    @Builder(builderMethodName = "childBuilder")
    public ActivityDto(Long id, Long objectId, String objectName, String descriptionEn, String descriptionRu, String descriptionUz, String createdDate) {
        super(id);
        this.objectId = objectId;
        this.objectName = objectName;
        this.descriptionEn = descriptionEn;
        this.descriptionRu = descriptionRu;
        this.descriptionUz = descriptionUz;
        this.createdDate = createdDate;
    }
}
