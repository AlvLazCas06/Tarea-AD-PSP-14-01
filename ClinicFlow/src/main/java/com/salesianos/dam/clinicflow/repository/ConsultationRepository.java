package com.salesianos.dam.clinicflow.repository;

import com.salesianos.dam.clinicflow.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
