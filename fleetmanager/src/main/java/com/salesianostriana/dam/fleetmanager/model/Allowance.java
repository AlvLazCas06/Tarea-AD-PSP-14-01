package com.salesianostriana.dam.fleetmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Allowance {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime finishDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    private Driver driver;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Allowance allowance = (Allowance) o;
        return getId() != null && Objects.equals(getId(), allowance.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
