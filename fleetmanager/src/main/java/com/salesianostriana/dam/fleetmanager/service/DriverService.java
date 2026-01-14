package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.exception.BadRequestException;
import com.salesianostriana.dam.fleetmanager.model.Driver;
import com.salesianostriana.dam.fleetmanager.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver createDriver(Driver driver) {
        if (driverRepository.existsByEmail(driver.getEmail())) {
            throw new BadRequestException();
        }
        return driverRepository.save(driver);
    }

    public Driver getDriverById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El conductor con el id: %d, no existe."));
        return driver;
    }

    public List<Driver> findAll() {
        List<Driver> drivers = driverRepository.findAll();
        if (drivers.isEmpty()) {
            throw new RuntimeException();
        }
        return drivers;
    }

}
