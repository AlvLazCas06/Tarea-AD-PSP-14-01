package com.salesianos.dam.clinicflow.exception;

public class ConsultationNotFoundException extends NotFoundException {

    public ConsultationNotFoundException(Long id) {
        super("La consulta con el id: %d, no existe".formatted(id));
    }

    public ConsultationNotFoundException() {
        super("No existen consultas en la base de datos.");
    }

}
