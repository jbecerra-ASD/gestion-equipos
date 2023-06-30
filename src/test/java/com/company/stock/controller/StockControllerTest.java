package com.company.stock.controller;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;
import com.company.stock.service.EquipoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @InjectMocks
    StockController stockController;

    @Mock
    EquipoService equipoService;

    EquipoRequest equipoRequest;

    @BeforeEach()
    public void initialize() {
        stockController = new StockController(equipoService);

        equipoRequest = new EquipoRequest("005","nombre",
                "descripcion", LocalDate.now(), 53000);
    }

    @Test
    @DisplayName("Registro equipo exitosamente")
    void registerSuccessful() {
        when(equipoService.registrar(any())).thenReturn(
                new EquipoResponse("001", "nombre", "descripcion",
                        LocalDate.now(), 5300000, 520000));

        ResponseEntity<EquipoResponse> answer = stockController.register(equipoRequest);

        assertEquals(answer.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(answer.getBody().numSerial());
    }

    @Test
    @DisplayName("Consultar equipo exitosamente")
    void find() {
        String numSerial = "001";
        when(equipoService.consultar(numSerial)).thenReturn(
                new EquipoResponse("001", "nombre", "descripcion",
                        LocalDate.now(), 5300000, 520000)
        );

        ResponseEntity<EquipoResponse> answer = stockController.find(numSerial);

        assertEquals(answer.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(answer.getBody());
    }

    @Test
    @DisplayName("Listar equipos exitosamente")
    void list() {
        when(equipoService.listar()).thenReturn(
                Arrays.asList(new EquipoResponse("001", "nombre", "descripcion",
                        LocalDate.now(), 5300000, 520000))
        );

        ResponseEntity<List<EquipoResponse>> answer = stockController.list();

        assertEquals(answer.getStatusCode().value(), HttpStatus.OK.value());
        assertEquals(1, answer.getBody().size());
    }

    @Test
    @DisplayName("Actualizar equipo exitosamente")
    void update() {
        when(equipoService.actualizar(equipoRequest)).thenReturn(
                new EquipoResponse("001", "actualizado", "descripcion",
                        LocalDate.now(), 5300000, 520000));

        ResponseEntity<EquipoResponse> answer = stockController.update(equipoRequest);

        assertEquals(answer.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(answer.getBody());
    }

    @Test
    @DisplayName("Eliminar equipo exitosamente")
    void delete() {
        String numSerial = "001";
        when(equipoService.eliminar(numSerial)).thenReturn(true);

        ResponseEntity<Boolean> answer = stockController.delete(numSerial);

        assertEquals(answer.getStatusCode().value(), HttpStatus.OK.value());
        assertEquals(true, answer.getBody());
    }
}