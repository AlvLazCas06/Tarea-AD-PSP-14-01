package com.salesianostriana.dam.fleetmanager.exception;

public class DriverNotFoundException extends NotFoundException {
    public DriverNotFoundException(String message) {
        super(message);
    }

    public DriverNotFoundException() {
        super("No hay conductores en la base de datos.");
    }

    public DriverNotFoundException(Long id) {
        super("El conductor con el id: %d, no existe.".formatted(id));
    }

}
