package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.dto.CreateAllowanceRequest;
import com.salesianostriana.dam.fleetmanager.model.Allowance;
import com.salesianostriana.dam.fleetmanager.model.Driver;
import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.repository.AllowanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final VehicleService vehicleService;
    private final DriverService driverService;

    public Allowance createAllowance(CreateAllowanceRequest dto) {
        Allowance allowance = dto.toEntity();
        Vehicle vehicle = vehicleService.getVehicleById(dto.idVehicle());
        Driver driver = driverService.getDriverById(dto.idDriver());
        if (vehicle.getStatus() != Status.AVAILABLE
                || (!allowanceRepository.findByDriver_Id(dto.idDriver()).isEmpty()
                && allowanceRepository.findByDriver_Id(dto.idDriver()).getLast() == null)) {
            throw new RuntimeException("No se puede tener un vehiculo que ya esta asignado.");
        }
        allowance.setDriver(driver);
        vehicle.addAllowance(allowance);
        return allowanceRepository.save(allowance);
    }

    public Allowance closeAllowance(Long idAllowance, Long idVehicle, Long idDriver) {
        Allowance allowance = allowanceRepository.findById(idAllowance)
                .orElseThrow(() -> new EntityNotFoundException("La asignación con el id: %d, no existe.".formatted(idAllowance)));
        Vehicle vehicle = vehicleService.getVehicleById(idVehicle);
        if (vehicle.getStatus() != Status.ASSIGNED
                || allowanceRepository.findByDriver_Id(idDriver).getLast() != null) {
            throw new RuntimeException();
        }
        vehicle.changeToAvailable();
        allowance.setFinishDate(LocalDateTime.now());
        return allowanceRepository.save(allowance);
    }

}
