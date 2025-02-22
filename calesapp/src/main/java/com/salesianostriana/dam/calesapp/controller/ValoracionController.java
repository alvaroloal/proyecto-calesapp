package com.salesianostriana.dam.calesapp.controller;

import com.salesianostriana.dam.calesapp.dto.valoracion.CreateUpdateValoracionDTO;
import com.salesianostriana.dam.calesapp.dto.valoracion.ValoracionDTO;
import com.salesianostriana.dam.calesapp.model.Valoracion;
import com.salesianostriana.dam.calesapp.service.ValoracionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos las valoraciones", description = "Retorna todas las valoraciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoraciones encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValoracionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ValoracionDTO>> getAllValoraciones() {
        List<ValoracionDTO> valoracionesDTO = valoracionService.findAll().stream()
                .map(ValoracionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(valoracionesDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una valoración por ID", description = "Retorna una valoración basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoracion encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValoracionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Valoracion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ValoracionDTO> getValoracionById(
            @Parameter(description = "ID de la valoracion a buscar", required = true)
            @PathVariable("id") Long id) {
        ValoracionDTO valoracion = ValoracionDTO.fromEntity(valoracionService.findById(id).get());
        return ResponseEntity.ok(valoracion);
    }

    @PostMapping
    @Operation(summary = "Crear una valoracion", description = "Crea una nueva valoracion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoración creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValoracionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ValoracionDTO> createValoracion(
            @Parameter(description = "Datos de la valoración a crear", required = true)
            @RequestBody CreateUpdateValoracionDTO valoracionDTO) {
        Valoracion valoracion = valoracionService.create(valoracionDTO);
        return ResponseEntity.ok(ValoracionDTO.fromEntity(valoracion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una valoración ", description = "Actualiza una valoración  basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoración actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValoracionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Valoracion no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ValoracionDTO> updateValoracion(
            @Parameter(description = "ID de la valoracion a actualizar", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Datos del valoracion a actualizar", required = true)
            @RequestBody CreateUpdateValoracionDTO valoracionDTO) {
        Valoracion valoracion = valoracionService.update(id, valoracionDTO).get();
        return ResponseEntity.ok(ValoracionDTO.fromEntity(valoracion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un valoracion", description = "Elimina una valoración basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoracion eliminado"),
            @ApiResponse(responseCode = "404", description = "Valoracion no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> deleteValoracion(
            @Parameter(description = "ID de la valoracion a eliminar", required = true)
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(valoracionService.delete(id));
    }


}
