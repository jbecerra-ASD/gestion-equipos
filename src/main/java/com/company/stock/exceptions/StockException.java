package com.company.stock.exceptions;

public class StockException extends RuntimeException {

    public StockException(String mensaje, Exception excepcion) {
        super(mensaje, excepcion);
    }
    public StockException(String mensaje) {
        super(mensaje);
    }
    
}
