package com.salesianos.dam.clinicflow.service;

import com.salesianos.dam.clinicflow.exception.ProfessionalNotFoundException;
import com.salesianos.dam.clinicflow.model.Professional;
import com.salesianos.dam.clinicflow.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public Professional findProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundException(id));
    }

}
