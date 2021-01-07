package uz.com.dto.location;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.location.Country;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDto extends GenericDto {

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;
    private Country country;

    @Builder(builderMethodName = "childBuilder")
    public DestinationDto(Long id, String name, String nameRu, String nameEn, String nameUz, Country country) {
        super(id);
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.country = country;
    }
}
