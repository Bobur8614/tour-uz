package uz.com.dto.location;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.location.Region;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto extends GenericDto {

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;
    private List<Region> regions;

    @Builder(builderMethodName = "childBuilder")
    public CountryDto(Long id, String name, String nameRu, String nameEn, String nameUz, List<Region> regions) {
        super(id);
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.regions = regions;
    }
}
