package uz.com.hibernate.domain.tourPackage;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.location.Country;
import uz.com.hibernate.domain.location.Destination;
import uz.com.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tour_package")
public class TourPackage extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id",  referencedColumnName = "id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id",  referencedColumnName = "id")
    private Destination destination;


    @Column(name = "start")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime start;

    @Column(name = "finish")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime finish;

    @Column(name = "top_deadline")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime topDeadline;

    @Column(name = "price")
    private Double price;

    @Column(name = "hotel", columnDefinition = "boolean default false")
    private Boolean hotel;

    @Column(name = "meal", columnDefinition = "boolean default false")
    private Boolean meal;

    @Column(name = "document", columnDefinition = "boolean default false")
    private Boolean document;

    @Column(name = "top", columnDefinition = "boolean default false")
    private Boolean top;

    @Embedded
    private AuditInfo auditInfo;
}
