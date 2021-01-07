package uz.com.hibernate.domain.company;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.contact.AwareAddress;
import uz.com.hibernate.domain.contact.Contact;
import uz.com.hibernate.domain.contact.PhoneNumber;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.hibernate.domain.location.District;
import uz.com.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "company")
public class Company extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "tin")
    private String tin;

    @Column(name = "email")
    private String email;

    @Column(name = "count_top")
    private Integer countTop = 0;

    @Column(name = "deadline")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id",  referencedColumnName = "id")
    private Contact contact;

    @OneToOne
    @JoinColumn(name = "resource_file", referencedColumnName = "id")
    private ResourceFile logo;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "company_file", joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_file_id")})
    private List<ResourceFile> resourceFiles = new ArrayList<>();

}
