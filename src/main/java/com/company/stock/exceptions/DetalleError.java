package com.company.stock.exceptions;

public class DetalleError {

    private String mensaje;

    public DetalleError(String mensaje) {
        super();
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}