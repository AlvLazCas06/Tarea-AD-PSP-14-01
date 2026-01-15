package com.salesianos.dam.clinicflow.service;

import com.salesianos.dam.clinicflow.exception.PatientNotFoundException;
import com.salesianos.dam.clinicflow.model.Patient;
import com.salesianos.dam.clinicflow.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

}
