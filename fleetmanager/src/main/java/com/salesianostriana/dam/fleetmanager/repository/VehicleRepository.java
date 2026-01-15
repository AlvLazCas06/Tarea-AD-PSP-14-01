package com.salesianostriana.dam.fleetmanager.repository;

import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {

    boolean existsByPlate(String plate);

    @EntityGraph(attributePaths = {"allowances", "maintenances"})
    Page<Vehicle> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"allowances", "maintenances"})
    Page<Vehicle> findAllByStatus(Status status, Pageable pageable);

}
