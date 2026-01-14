package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.model.Driver;
import com.salesianostriana.dam.fleetmanager.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver getDriverById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El conductor con el id: %d, no existe."));
        return driver;
    }

}
