package com.company.stock.service;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;

import java.util.List;


public interface EquipoService {

    EquipoResponse registrar(EquipoRequest equipoRequest);

    EquipoResponse consultar(String serial);

    List<EquipoResponse> listar();

    EquipoResponse actualizar(EquipoRequest equipoRequest);

    boolean eliminar(String serial);

}
