package com.company.stock.mapper;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;
import com.company.stock.model.Equipo;

import java.util.List;

public interface EquipoMapper {

      Equipo mapToEntity(EquipoRequest equipoRequest, double valorDepreciado);

      EquipoResponse mapToEquipoResponse(Equipo equipo);

      List<EquipoResponse> mapToListEquipoResponse(List<Equipo> equipos);

}
