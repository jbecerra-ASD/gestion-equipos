package com.company.stock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    private String numSerial;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_compra", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCompra;

    @Column(name = "valor_compra", nullable = false)
    private double valorCompra;

    @Column(name = "valor_depreciado", nullable = false)
    private double valorDepreciado;
}
