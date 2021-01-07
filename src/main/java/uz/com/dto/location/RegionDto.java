package uz.com.dto.location;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.location.Country;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto extends GenericDto {

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;
    private Country country;
    private List<DistrictDto> districts;

    @Builder(builderMethodName = "childBuilder")
    public RegionDto(Long id, String name, String nameRu, String nameEn, String nameUz, Country country, List<DistrictDto> districts) {
        super(id);
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.country = country;
        this.districts = districts;
    }
}
