package com.salesianostriana.dam.calesapp.controller;

import com.salesianostriana.dam.calesapp.dto.servicio.CreateUpdateServicioDTO;
import com.salesianostriana.dam.calesapp.dto.servicio.ServicioDTO;
import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.service.ServicioService;
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
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas los servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServicioDTO.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron servicios")
    })
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        List<ServicioDTO> serviciosDTO = servicioService.findAll().stream()
                .map(ServicioDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviciosDTO);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener un servicio por su ID", description = "Retorna un servicio basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServicioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<ServicioDTO> getServicioById(
            @Parameter(description = "ID del servicio a buscar", required = true)
            @PathVariable("id") Long id) {
        ServicioDTO servicio = ServicioDTO.fromEntity(servicioService.findById(id).get());
        return ResponseEntity.ok(servicio);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo servicio", description = "Crea un nuevo servicio y retorna los datos creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio creada con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServicioDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ServicioDTO> createServicio(
            @Parameter(description = "Datos del servicio a crear", required = true)
            @RequestBody CreateUpdateServicioDTO servicioDTO) {
        Servicio createdServicio = servicioService.create(servicioDTO);
        return ResponseEntity.ok(ServicioDTO.fromEntity(createdServicio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un servicio existente", description = "Actualiza un servicio basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio actualizado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServicioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ServicioDTO> updateServicio(
            @Parameter(description = "ID del servicio a actualizar", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos datos del servicio", required = true)
            @RequestBody CreateUpdateServicioDTO servicioDTO) {
        return servicioService.update(id, servicioDTO)
                .map(servicio -> ResponseEntity.ok(ServicioDTO.fromEntity(servicio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un servicio por su id", description = "Elimina un servicio basada en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servicio eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteServicio(
            @Parameter(description = "ID del servicio a eliminar", required = true)
            @PathVariable("id") Long id) {
        if (servicioService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
