package com.company.stock.service;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;
import com.company.stock.exceptions.EquipoNoEncontradoException;
import com.company.stock.exceptions.StockException;
import com.company.stock.mapper.EquipoMapper;
import com.company.stock.model.Equipo;
import com.company.stock.repository.EquipoRepository;
import com.company.stock.util.DepreciacionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoServiceImp implements EquipoService {

    private final EquipoRepository equipoRepository;

    private final EquipoMapper equipoMapper;

    private final DepreciacionUtil depreciacionUtil;

    @Override
    public EquipoResponse registrar(EquipoRequest equipoRequest) {
        double valorDepreciado = depreciacionUtil.calcularValorDepreciado(equipoRequest.valorCompra(),
                equipoRequest.fechaCompra());
        Equipo equipo = equipoMapper.mapToEntity(equipoRequest, valorDepreciado);
        equipoRepository.save(equipo);
        return equipoMapper.mapToEquipoResponse(equipo);
    }

    @Override
    public EquipoResponse consultar(String serial) {
        Equipo equipo = getEquipo(serial);
        return equipoMapper.mapToEquipoResponse(equipo);
    }

    private Equipo getEquipo(String serial) {
        Equipo equipo = equipoRepository.findById(serial)
                .orElseThrow(() -> new EquipoNoEncontradoException("Equipo no encontrado"));
        return equipo;
    }

    @Override
    public List<EquipoResponse> listar() {
        List<Equipo> equipos = (List<Equipo>) equipoRepository.findAll();
        return equipoMapper.mapToListEquipoResponse(equipos);
    }

    @Override
    public EquipoResponse actualizar(EquipoRequest equipoRequest) {
        if (equipoRequest.numSerial() == null) {
            throw new StockException("Debe indicar el serial del equipo");
        } else {
            getEquipo(equipoRequest.numSerial());
        }

        double valorDepreciado = depreciacionUtil.calcularValorDepreciado(equipoRequest.valorCompra(),
                equipoRequest.fechaCompra());

        Equipo equipo = equipoMapper.mapToEntity(equipoRequest, valorDepreciado);
        equipoRepository.save(equipo);
        return equipoMapper.mapToEquipoResponse(equipo);
    }

    @Override
    public boolean eliminar(String serial) {
        Equipo equipo = getEquipo(serial);
        boolean eliminado = false;
        try {
            equipoRepository.delete(equipo);
            eliminado = true;
        } finally {
            return eliminado;
        }
    }

}
