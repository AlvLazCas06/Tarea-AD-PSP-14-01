package com.salesianos.dam.clinicflow.error;

import com.salesianos.dam.clinicflow.exception.BadRequestException;
import com.salesianos.dam.clinicflow.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Entidad no encontrada");
        pd.setType(URI.create("https://www.salesianos-triana.com/not-found"));
        return pd;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Datos erróneos");
        pd.setType(URI.create("https://www.salesianos-triana.com/bad-request"));
        return pd;
    }

}
