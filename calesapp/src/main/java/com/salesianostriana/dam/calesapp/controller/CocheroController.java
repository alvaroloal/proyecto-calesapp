package com.salesianostriana.dam.calesapp.controller;

import com.salesianostriana.dam.calesapp.dto.cochero.CocheroDTO;
import com.salesianostriana.dam.calesapp.dto.cochero.CocheroListDTO;
import com.salesianostriana.dam.calesapp.dto.cochero.CreateUpdateCocheroDTO;
import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.service.CocheroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cocheros")
public class CocheroController {

        private final CocheroService cocheroService;

        public CocheroController(CocheroService cocheroService) {
                this.cocheroService = cocheroService;
        }

        @Operation(summary = "Obtener la lista de cocheros")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de cocheros obtenida con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CocheroDTO.class))),
                        @ApiResponse(responseCode = "204", description = "No se encontraron cocheros")
        })
        @GetMapping
        public CocheroListDTO getAllCocheros() {
                return CocheroListDTO.of(cocheroService.findAll());
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener un cochero por su ID", description = "Retorna un cochero basado en su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cochero encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CocheroDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Cochero no encontrada")
        })
        public ResponseEntity<CocheroDTO> getCocheroById(
                        @Parameter(description = "ID del cochero a buscar", required = true) @PathVariable("id") Long id) {
                CocheroDTO cochero = CocheroDTO.fromEntity(cocheroService.findById(id).get());
                return ResponseEntity.ok(cochero);
        }

        @PostMapping
        @Operation(summary = "Crear un nuevo cochero", description = "Crea una nuevo cochero y retorna los datos creados")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cochero creado con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CocheroDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        public ResponseEntity<CocheroDTO> createCochero(
                        @Parameter(description = "Datos del cochero a crear", required = true) @RequestBody CreateUpdateCocheroDTO cocheroDTO) {
                Cochero createdCochero = cocheroService.create(cocheroDTO);
                return ResponseEntity.ok(CocheroDTO.fromEntity(createdCochero));
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar una cochero existente", description = "Actualiza una cochero existente basada en su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cochero actualizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CocheroDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Cochero no encontrado"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        public ResponseEntity<CocheroDTO> updateCochero(
                        @Parameter(description = "ID del cochero a actualizar", required = true) @PathVariable("id") Long id,
                        @Parameter(description = "Nuevos datos del cochero", required = true) @RequestBody CreateUpdateCocheroDTO cocheroDTO) {
                return cocheroService.update(id, cocheroDTO)
                                .map(cochero -> ResponseEntity.ok(CocheroDTO.fromEntity(cochero)))
                                .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar una cochero por su id", description = "Elimina un cochero basado en su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Cochero eliminada con éxito"),
                        @ApiResponse(responseCode = "404", description = "Cochero no encontrada"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        public ResponseEntity<Void> deleteCochero(
                        @Parameter(description = "ID del cochero a eliminar", required = true) @PathVariable("id") Long id) {
                if (cocheroService.delete(id)) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.notFound().build();
        }

}
