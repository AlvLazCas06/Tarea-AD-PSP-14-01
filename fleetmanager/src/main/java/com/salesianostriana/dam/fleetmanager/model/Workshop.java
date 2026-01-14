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
public class Workshop {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String city;

    @OneToMany(mappedBy = "workshop", fetch = FetchType.LAZY)
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
        Workshop workshop = (Workshop) o;
        return getId() != null && Objects.equals(getId(), workshop.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void addMaintenance(Maintenance maintenance) {
        this.getMaintenances().add(maintenance);
        maintenance.setWorkshop(this);
    }

}
