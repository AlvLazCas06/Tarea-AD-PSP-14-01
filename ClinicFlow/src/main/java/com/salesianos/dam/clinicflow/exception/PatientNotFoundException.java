package com.salesianos.dam.clinicflow.exception;

public class PatientNotFoundException extends NotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(Long id) {
        super("El paciente con el id: %d, no existe".formatted(id));
    }

    public PatientNotFoundException() {
        super("No existen pacientes");
    }

}
