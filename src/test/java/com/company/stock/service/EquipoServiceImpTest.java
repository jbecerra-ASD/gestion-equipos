package com.company.stock.service;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;
import com.company.stock.exceptions.EquipoNoEncontradoException;
import com.company.stock.exceptions.StockException;
import com.company.stock.mapper.EquipoMapper;
import com.company.stock.model.Equipo;
import com.company.stock.repository.EquipoRepository;
import com.company.stock.util.DepreciacionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipoServiceImpTest {

    @InjectMocks
    EquipoServiceImp equipoServiceImp;

    @Mock
    EquipoRepository equipoRepository;

    @Mock
    EquipoMapper equipoMapper;

    @Mock
    DepreciacionUtil depreciacionUtil;

    EquipoRequest equipoRequest;

    Equipo equipo;

    @BeforeEach()
    public void initialize() {
        equipoServiceImp = new EquipoServiceImp(equipoRepository, equipoMapper, depreciacionUtil);

        equipoRequest = new EquipoRequest("001", "nombre", "descripcion",
                LocalDate.now(), 5300000);

        equipo = new Equipo("001", "nombre", "descripcion",
                LocalDate.now(), 5300000, 520000);
    }

    @Test
    @DisplayName("Registro equipo exitosamente")
    void registrarExitosamente() {
        Equipo equipo = new Equipo();
        equipo.setNumSerial("001");
        equipo.setNombre("nombre");
        equipo.setDescripcion("descripcion");
        equipo.setFechaCompra(LocalDate.now());
        equipo.setValorCompra(5300000);
        equipo.setValorDepreciado(5220000);

        when(depreciacionUtil.calcularValorDepreciado(5300000, LocalDate.now()))
                .thenReturn(5220000d);

        when(equipoMapper.mapToEntity(equipoRequest, 5220000d)).thenReturn(equipo);

        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipo);

        when(equipoMapper.mapToEquipoResponse(equipo))
                .thenReturn(new EquipoResponse("001", "nombre",
                        "descripcion", LocalDate.now(), 5300000, 520000));

        EquipoResponse result = equipoServiceImp.registrar(equipoRequest);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Consultar equipo exitosamente")
    void consultarExitosamente() {
        when(equipoRepository.findById(anyString()))
                .thenReturn(Optional.of(equipo));

        lenient().when(equipoMapper.mapToEquipoResponse(any(Equipo.class))).thenReturn(
                new EquipoResponse("001", "nombre",
                        "descripcion", LocalDate.now(), 25, 22));

        EquipoResponse result = equipoServiceImp.consultar(anyString());

        assertNotNull(result);
        assertEquals("001", result.numSerial());
    }

    @Test
    @DisplayName("Listar todos los equipo exitosamente")
    void listarTodosExitosamente() {
        when(equipoRepository.findAll())
                .thenReturn(Arrays.asList(equipo));

        when(equipoMapper.mapToListEquipoResponse(Arrays.asList(equipo)))
                .thenReturn(Arrays.asList(new EquipoResponse("001", "nombre",
                        "descripcion", LocalDate.now(), 53000000, 5200000)));

        List<EquipoResponse> result = equipoServiceImp.listar();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Actualizar equipo exitosamente")
    void actualizarExitosamente() {
        Equipo equipoExpected = new Equipo();
        equipoExpected.setNumSerial("001");
        equipoExpected.setNombre("nombre");
        equipoExpected.setDescripcion("descripcion");
        equipoExpected.setFechaCompra(LocalDate.now());
        equipoExpected.setValorCompra(5342445);

        when(equipoRepository.findById(anyString()))
                .thenReturn(Optional.of(equipo));

        when(equipoRepository.save(any(Equipo.class)))
                .thenReturn(equipo);

        when(equipoMapper.mapToEntity(any(EquipoRequest.class), anyDouble()))
                .thenReturn(equipo);

        when(equipoMapper.mapToEquipoResponse(any(Equipo.class))).thenReturn(
                new EquipoResponse("001", "nombre",
                        "descripcion", LocalDate.now(), 25, 22));

        EquipoResponse result = equipoServiceImp.actualizar(equipoRequest);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Actualizar equipo sin indicar serial")
    void actualizarEquipoSinSerialLanzaExcepcion() {
        EquipoRequest equipoRequestSinSerial = new EquipoRequest(null,
                "nombre", "descripcion", LocalDate.now(), 520000);

        assertThrows(StockException.class, () -> equipoServiceImp.actualizar(equipoRequestSinSerial),
                "Debe indicar el serial del equipo");
    }

    @Test
    @DisplayName("Eliminar equipo exitosamente")
    void eliminarExitosamente() {
        when(equipoRepository.findById(anyString()))
                .thenReturn(Optional.of(equipo));

        boolean resultado = equipoServiceImp.eliminar(anyString());
        assertEquals(Boolean.TRUE, resultado);
    }

}