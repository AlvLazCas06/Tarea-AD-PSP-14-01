package com.salesianostriana.dam.fleetmanager.repository;

import com.salesianostriana.dam.fleetmanager.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository
        extends JpaRepository<Workshop, Long> {
}
