package uz.com.hibernate.domain.contact;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.location.District;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contacts")
public class Contact extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id",  referencedColumnName = "id")
    private District district;

    @Column(name = "street")
    private String street;

    @Column(name = "home")
    private String home;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @Embedded
    private AuditInfo auditInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    @Builder.Default
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    @Builder.Default
    private List<AwareAddress> awareAddresses = new ArrayList<>();;

}
