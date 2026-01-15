package com.salesianos.dam.clinicflow.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }

    public AppointmentNotFoundException(Long id) {
        super("La cita con el id: %d, no existe.".formatted(id));
    }

    public AppointmentNotFoundException() {
        super("No existen citas en la base de datos.");
    }

}
