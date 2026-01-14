package com.salesianostriana.dam.fleetmanager.repository;

import com.salesianostriana.dam.fleetmanager.model.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowanceRepository
        extends JpaRepository<Allowance, Long> {
}
