package uz.com.dto.location;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.location.Region;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto extends GenericDto {

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;
    private Region region;


    @Builder(builderMethodName = "childBuilder")
    public DistrictDto(Long id, String name, String nameRu, String nameEn, String nameUz, Region region) {
        super(id);
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.region = region;
    }
}