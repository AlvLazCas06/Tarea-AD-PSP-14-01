package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.model.Allowance;
import com.salesianostriana.dam.fleetmanager.model.Driver;
import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.repository.AllowanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final VehicleService vehicleService;
    private final DriverService driverService;

    public Allowance createAllowance(Allowance allowance, Long idVehicle, Long idDriver) throws EntityNotFoundException {
        Vehicle vehicle = vehicleService.getVehicleById(idVehicle);
        Driver driver = driverService.getDriverById(idDriver);
        if (vehicle.getStatus() == Status.ASSIGNED) {
            throw new RuntimeException("No se puede tener un vehiculo que ya esta asignado.");
        }
        allowance.setDriver(driver);
        allowance.setVehicle(vehicle);
        return allowanceRepository.save(allowance);
    }

}
