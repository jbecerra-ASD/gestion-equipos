package com.company.stock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EquipoRequest(String numSerial, String nombre, String descripcion,
                            @JsonFormat(pattern = "dd-MM-yyyy") LocalDate fechaCompra, double valorCompra) {

}
