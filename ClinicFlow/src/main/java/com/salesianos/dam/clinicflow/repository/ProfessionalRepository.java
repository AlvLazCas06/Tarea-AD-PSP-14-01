package com.salesianos.dam.clinicflow.repository;

import com.salesianos.dam.clinicflow.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
