package com.company.stock.mapper;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;
import com.company.stock.model.Equipo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipoMapperImpl implements EquipoMapper {

    @Override
    public Equipo mapToEntity(EquipoRequest equipoRequest, double valorDepreciado) {
        return new Equipo(equipoRequest.numSerial(), equipoRequest.nombre(),
                equipoRequest.descripcion(), equipoRequest.fechaCompra(),
                equipoRequest.valorCompra(), valorDepreciado);
    }

    @Override
    public EquipoResponse mapToEquipoResponse(Equipo equipo) {
        return new EquipoResponse(equipo.getNumSerial(), equipo.getNombre(), equipo.getDescripcion(),
                equipo.getFechaCompra(), equipo.getValorCompra(), equipo.getValorDepreciado());
    }

    @Override
    public List<EquipoResponse> mapToListEquipoResponse(List<Equipo> equipos) {
        return equipos.stream().map((equipo) -> {
            return mapToEquipoResponse(equipo);
        }).collect(Collectors.toList());
    }
}
