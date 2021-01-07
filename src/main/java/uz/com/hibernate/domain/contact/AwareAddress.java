package uz.com.hibernate.domain.contact;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.settings.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "aware_addresses")
public class AwareAddress extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aware_id",  referencedColumnName = "id")
    private Type aware;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id",  referencedColumnName = "id")
    private Contact contact;

    @Column(name = "address")
    private String address;

    @Embedded
    private AuditInfo auditInfo;

}
