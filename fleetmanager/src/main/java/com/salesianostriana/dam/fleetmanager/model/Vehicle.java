package com.salesianostriana.dam.fleetmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String plate;

    private int currentKm;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Allowance> allowances = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Maintenance> maintenances = new ArrayList<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Vehicle vehicle = (Vehicle) o;
        return getId() != null && Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
