package uz.com.dto.file;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.TypeDto;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFileDto extends GenericDto {
    private final String now = LocalDateTime.now().toString();
    private TypeDto objectType;
    private Long objectId;
    private String name;
    private String url;
    private String mimeType;
    private Long size;
    private boolean isDefault;
    private String createdAt;

    @Builder(builderMethodName = "childBuilder")
    public ResourceFileDto(Long id, TypeDto objectType, Long objectId, String name, String url, String mimeType, Long size, boolean isDefault, String createdAt) {
        super(id);
        this.objectType = objectType;
        this.objectId = objectId;
        this.name = name;
        this.url = url;
        this.mimeType = mimeType;
        this.size = size;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
    }
}
