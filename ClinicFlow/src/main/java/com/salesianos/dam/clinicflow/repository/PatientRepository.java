package com.salesianos.dam.clinicflow.repository;

import com.salesianos.dam.clinicflow.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
