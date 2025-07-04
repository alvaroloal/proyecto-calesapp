package com.salesianostriana.dam.calesapp.controller;

import com.salesianostriana.dam.calesapp.dto.parada.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.dto.parada.ParadaDTO;
import com.salesianostriana.dam.calesapp.dto.parada.ParadaListDTO;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.service.ParadaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

//@CrossOrigin(origins = "http://localhost:4200")
@Log
@RestController
@RequestMapping("/api/paradas")
public class ParadaController {

        private final ParadaService paradaService;

        public ParadaController(ParadaService paradaService) {
                this.paradaService = paradaService;
        }

        // se puede sobrescribir desde la URL con
        // ?page=0&size=20&sort=nombre,asc.
        @GetMapping
        @Operation(summary = "Obtener la lista paginada de paradas")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParadaListDTO.class))),
                        @ApiResponse(responseCode = "204", description = "No se encontraron paradas")
        })
        public ResponseEntity<ParadaListDTO> getAllParadas(
                        @Parameter(hidden = true) @PageableDefault(size = 10, page = 0) Pageable pageable) {

                Page<Parada> paradaPage = paradaService.findAll(pageable);

                if (paradaPage.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }

                List<ParadaDTO> paradaDTOs = paradaPage.getContent().stream()
                                .map(ParadaDTO::of)
                                .toList();

                return ResponseEntity.ok(new ParadaListDTO(paradaPage.getTotalElements(), paradaDTOs));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener una parada por su ID", description = "Retorna una parada basada en su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Parada encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParadaDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Parada no encontrada")
        })
        public ResponseEntity<ParadaDTO> getParadaById(
                        @Parameter(description = "ID de la parada a buscar", required = true) @PathVariable("id") Long id) {
                ParadaDTO parada = ParadaDTO.fromEntity(paradaService.findById(id).get());
                return ResponseEntity.ok(parada);
        }

        @PostMapping
        @Operation(summary = "Crear una nueva parada", description = "Crea una nueva parada y retorna los datos creados")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Parada creada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParadaDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        public ResponseEntity<ParadaDTO> createParada(
                        @Parameter(description = "Datos de la parada a crear", required = true) @RequestBody @Valid CreateUpdateParadaDTO paradaDTO) {
                Parada createdParada = paradaService.create(paradaDTO);
                return ResponseEntity.ok(ParadaDTO.fromEntity(createdParada));
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar una parada existente", description = "Actualiza una parada existente basada en su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Parada actualizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParadaDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Parada no encontrada"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        public ResponseEntity<ParadaDTO> updateParada(
                        @Parameter(description = "ID de la parada a actualizar", required = true) @PathVariable("id") Long id,
                        @Parameter(description = "Nuevos datos de la parada", required = true) @RequestBody CreateUpdateParadaDTO paradaDTO) {
                return paradaService.update(id, paradaDTO)
                                .map(parada -> ResponseEntity.ok(ParadaDTO.fromEntity(parada)))
                                .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar una parada por su id", description = "Elimina una parada basada en su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Parada eliminada con éxito"),
                        @ApiResponse(responseCode = "404", description = "Parada no encontrada"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        public ResponseEntity<Void> deleteParada(
                        @Parameter(description = "ID de la parada a eliminar", required = true) @PathVariable("id") Long id) {
                if (paradaService.delete(id)) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.notFound().build();
        }

        @GetMapping("/buscar-nombre")
        public ResponseEntity<List<ParadaDTO>> buscarPorNombre(@RequestParam("nombre") String nombre) {
                List<Parada> resultados = paradaService.buscarPorNombre(nombre);
                List<ParadaDTO> resultadoDTO = resultados.stream()
                                .map(ParadaDTO::fromEntity)
                                .toList();
                return ResponseEntity.ok(resultadoDTO);
        }

}