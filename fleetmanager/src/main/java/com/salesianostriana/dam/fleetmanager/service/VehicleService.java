package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.exception.BadRequestException;
import com.salesianostriana.dam.fleetmanager.exception.VehicleNotFoundException;
import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    /**
     * Ejemplo de búsqueda de vehículos usando Specifications con filtros dinámicos.
     * Los filtros se combinan con AND, permitiendo búsquedas muy flexibles.
     *
     * @param plate Matrícula parcial a buscar (opcional)
     * @param status Estado del vehículo (opcional)
     * @param minKm Kilometraje mínimo (opcional)
     * @param maxKm Kilometraje máximo (opcional)
     * @param pageable Información de paginación
     * @return Página de vehículos que cumplen los criterios
     *
     * Ejemplos de uso:
     * - searchVehicles(null, Status.AVAILABLE, null, null, pageable) -> Solo disponibles
     * - searchVehicles("ABC", null, null, null, pageable) -> Matrículas que contengan "ABC"
     * - searchVehicles(null, null, 50000, 100000, pageable) -> Vehículos con 50k-100k km
     * - searchVehicles("123", Status.AVAILABLE, null, 80000, pageable) -> Combina múltiples filtros
     */
    public Page<Vehicle> searchVehicles(String plate, Status status, Integer minKm, Integer maxKm, Pageable pageable) {
        // Creamos la specification combinando múltiples condiciones con AND
        Specification<Vehicle> spec = Specification.where(VehicleRepository.plateContains(plate))
                .and(VehicleRepository.hasStatus(status))
                .and(VehicleRepository.hasKmBetween(minKm, maxKm));

        // Ejecutamos la consulta con la specification
        Page<Vehicle> vehicles = vehicleRepository.findAll(spec, pageable);

        if (vehicles.isEmpty()) {
            throw new VehicleNotFoundException();
        }

        return vehicles;
    }

    /**
     * Otro ejemplo: Buscar vehículos disponibles con bajo kilometraje
     * Este método muestra cómo crear búsquedas específicas reutilizando specifications
     */
    public Page<Vehicle> findAvailableVehiclesWithLowKm(int maxKm, Pageable pageable) {
        Specification<Vehicle> spec = Specification
                .where(VehicleRepository.hasStatus(Status.AVAILABLE))
                .and(VehicleRepository.hasKmLessThanOrEqual(maxKm));

        return vehicleRepository.findAll(spec, pageable);
    }

    /**
     * Ejemplo: Buscar vehículos en mantenimiento con alto kilometraje
     */
    public Page<Vehicle> findVehiclesUnderMaintenanceWithHighKm(int minKm, Pageable pageable) {
        Specification<Vehicle> spec = Specification
                .where(VehicleRepository.hasStatus(Status.UNDER_MAINTENANCE))
                .and(VehicleRepository.hasKmGreaterThanOrEqual(minKm));

        return vehicleRepository.findAll(spec, pageable);
    }

}
