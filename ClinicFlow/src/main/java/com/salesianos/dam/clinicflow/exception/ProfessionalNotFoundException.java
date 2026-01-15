package com.salesianos.dam.clinicflow.exception;

public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(String message) {
        super(message);
    }

    public ProfessionalNotFoundException(Long id) {
        super("El profesional con el id: %d, no existe.".formatted(id));
    }

    public ProfessionalNotFoundException() {
        super("No existen profesionales.");
    }

}
