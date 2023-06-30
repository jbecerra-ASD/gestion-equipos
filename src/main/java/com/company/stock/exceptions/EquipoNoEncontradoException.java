package com.company.stock.exceptions;

public class EquipoNoEncontradoException extends RuntimeException{
    public EquipoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
