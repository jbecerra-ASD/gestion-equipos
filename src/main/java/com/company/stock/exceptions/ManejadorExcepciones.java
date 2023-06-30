package com.company.stock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ManejadorExcepciones extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EquipoNoEncontradoException.class)
    public final ResponseEntity<DetalleError> manejadorEquipoNoEncontrado(EquipoNoEncontradoException ex) {
        DetalleError detalleError = new DetalleError(ex.getMessage());
        return new ResponseEntity(detalleError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockException.class)
    public final ResponseEntity<DetalleError> manejadorStockGeneral(StockException ex) {
        DetalleError detalleError = new DetalleError(ex.getMessage());
        return new ResponseEntity(detalleError, HttpStatus.BAD_REQUEST);
    }

}