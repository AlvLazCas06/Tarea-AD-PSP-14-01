package com.salesianos.dam.clinicflow.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super("Los datos introducidos no son validos.");
    }

}
