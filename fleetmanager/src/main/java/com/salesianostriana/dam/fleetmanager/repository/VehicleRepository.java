package com.salesianostriana.dam.fleetmanager.repository;

import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {

    boolean existsByPlate(String plate);

}
