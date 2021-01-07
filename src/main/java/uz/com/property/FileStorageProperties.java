package uz.com.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "file.prop")
public class FileStorageProperties {

    private String uploadDir;
}
