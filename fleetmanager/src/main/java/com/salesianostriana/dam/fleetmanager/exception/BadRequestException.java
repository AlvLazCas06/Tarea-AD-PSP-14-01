package com.salesianostriana.dam.fleetmanager.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super("Los datos introducidos son incorrectos");
    }

}
