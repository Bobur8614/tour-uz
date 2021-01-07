package uz.com.hibernate.domain.activity;

import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.springframework.data.annotation.CreatedDate;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "state <> 2")
@Table(name = "activity")
public class Activity extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "descriptionEn", length = 3000)
    private String descriptionEn;

    @Column(name = "descriptionRu", length = 3000)
    private String descriptionRu;

    @Column(name = "descriptionUz", length = 3000)
    private String descriptionUz;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_name")
    private String objectName;

    @Embedded
    private AuditInfo auditInfo;


    /*@JsonProperty(value = "date")
    public String getDate() {
        return ServerUtils.period(createdDate);
    }*/

    /*@JsonProperty(value = "description")
    public String toString() {
        switch (BaseUtils.getLanguage()) {
            case "uz":
                return descriptionUz;
            case "ru":
                return descriptionRu;
            case "en":
                return descriptionEn;
            default:
                return descriptionUz;
        }
    }*/

}
