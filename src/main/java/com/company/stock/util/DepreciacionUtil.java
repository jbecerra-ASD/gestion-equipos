package com.company.stock.util;

import com.company.stock.model.Equipo;
import com.company.stock.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DepreciacionUtil {

    private final EquipoRepository equipoRepository;

    @Value("${stock.depreciacion.porcentaje}")
    private double porcentajeDepreciacion;

    /**
     * Tarea programada encargada de actualizar el valor de los equipos de acuerdo
     * a la depreciacion anual, se ejecuta una vez al año cada primero de enero
     */
    @Scheduled(cron = "00 */1 * * * *")
    public void depreciarValorEquipos() {
        System.out.println("Se depreciaron los equipos");
        List<Equipo> equipos = (List<Equipo>) equipoRepository.findAll();
        List<Equipo> equiposDepreciados = equipos.stream().map((equipo -> {
            double valorDepreciado = calcularValorDepreciado(equipo.getValorDepreciado(), equipo.getFechaCompra());
            equipo.setValorDepreciado(valorDepreciado);
            return equipo;
        })).collect(Collectors.toList());
        equipoRepository.saveAll(equiposDepreciados);
    }

    /**
     * Se calcula la depreciacion del equipo de acuerdo a el año y valor de compra,
     * el calculo se hace de acuerdo al porcentaje de depreciacion establecido
     * @param valorCompra valor de compra del equipo
     * @param fechaCompra fecha en la cual se adquirio el equipo
     * @return double valor depreciado del equipo
     */
    public double calcularValorDepreciado(double valorCompra, LocalDate fechaCompra) {
        int anosTranscurridos = LocalDate.now().getYear() - fechaCompra.getYear();
        if (anosTranscurridos > 0) {
            return Math.pow(valorCompra * (1 - (porcentajeDepreciacion/100)), anosTranscurridos);
        }
        return valorCompra;
    }

}
