package uz.com.hibernate.domain.payment;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.company.Company;
import uz.com.hibernate.domain.settings.Type;
import uz.com.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "paySum")
    private Double paySum;

    @Column(name = "start")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime payDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",  referencedColumnName = "id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_type_id",  referencedColumnName = "id")
    private Type payType;

    @Embedded
    private AuditInfo auditInfo;
}
