package com.salesianostriana.dam.fleetmanager.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException() {
        super("No hay vehículos en la base de datos.");
    }

    public VehicleNotFoundException(Long id) {
        super("El vehículo con el id: %d, no existe.".formatted(id));
    }
}
