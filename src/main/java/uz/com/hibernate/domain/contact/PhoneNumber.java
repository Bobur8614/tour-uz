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
@Table(name = "phone_number")
public class PhoneNumber extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_number_type_id",  referencedColumnName = "id")
    private Type phoneNumberType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id",  referencedColumnName = "id")
    private Contact contact;

    @Column(name = "number")
    private String number;

    @Embedded
    private AuditInfo auditInfo;

}
