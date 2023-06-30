package com.company.stock.controller;

import com.company.stock.dto.EquipoRequest;
import com.company.stock.dto.EquipoResponse;
import com.company.stock.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final EquipoService equipoService;

    @Operation(summary = "Registrar un equipo")
    @ApiResponse(responseCode = "200", description = "Equipo registrado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EquipoRequest.class))})
    @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    @PostMapping
    public ResponseEntity<EquipoResponse> register(@Parameter(description = "Equipo request",
            required = true) @RequestBody EquipoRequest equipoRequest) {
        return ResponseEntity.ok(equipoService.registrar(equipoRequest));
    }

    @Operation(summary = "Consultar un equipo por serial")
    @ApiResponse(responseCode = "200", description = "Equipo consultado", content = {
        @Content(mediaType = "application/json")})
    @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    @GetMapping("/{serial}")
    public ResponseEntity<EquipoResponse> find(@Parameter(name = "Serial del equipo", required = true)
                                                   @PathVariable String serial) {
        return ResponseEntity.ok(equipoService.consultar(serial));
    }

    @Operation(summary = "Listar todos los equipos")
    @ApiResponse(responseCode = "200", description = "Equipos consultados")
    @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    @GetMapping
    public ResponseEntity<List<EquipoResponse>> list() {
        return ResponseEntity.ok(equipoService.listar());
    }

    @Operation(summary = "Actualizar equipo")
    @ApiResponse(responseCode = "200", description = "Equipo actualizado")
    @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    @PutMapping
    public ResponseEntity<EquipoResponse> update(@RequestBody EquipoRequest equipoRequest) {
        return ResponseEntity.ok(equipoService.actualizar(equipoRequest));
    }

    @Operation(summary = "Eliminar equipo")
    @ApiResponse(responseCode = "200", description = "Equipo eliminado")
    @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    @DeleteMapping("/{serial}")
    public ResponseEntity<Boolean> delete(@Parameter(name = "Serial del equipo", required = true)
                                              @PathVariable String serial) {
        return ResponseEntity.ok(equipoService.eliminar(serial));
    }

}
