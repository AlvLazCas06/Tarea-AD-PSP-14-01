package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.exception.BadRequestException;
import com.salesianostriana.dam.fleetmanager.exception.VehicleNotFoundException;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsByPlate(vehicle.getPlate())) {
            throw new BadRequestException();
        }
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        return vehicle;
    }

    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
        if (vehicles.isEmpty()) {
            throw new VehicleNotFoundException();
        }
        return vehicles;
    }

}
