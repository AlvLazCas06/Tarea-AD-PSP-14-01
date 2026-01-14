package com.salesianostriana.dam.fleetmanager.repository;

import com.salesianostriana.dam.fleetmanager.model.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllowanceRepository
        extends JpaRepository<Allowance, Long> {

    List<Allowance> findByDriver_Id(Long id);

}
