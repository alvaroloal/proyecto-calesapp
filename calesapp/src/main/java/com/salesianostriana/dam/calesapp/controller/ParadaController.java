package com.salesianostriana.dam.calesapp.controller;

import com.salesianostriana.dam.calesapp.dto.parada.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.dto.parada.ParadaDTO;
import com.salesianostriana.dam.calesapp.dto.parada.ParadaListDTO;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.service.ParadaService;
import com.salesianostriana.dam.calesapp.user.util.SearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@CrossOrigin(origins = "http://localhost:4200")
@Log
@RestController
@RequestMapping("/api/paradas")
public class ParadaController {

        private final ParadaService paradaService;

        public ParadaController(ParadaService paradaService) {
                this.paradaService = paradaService;
        }

        @Operation(summary = "Obtener la lista de paradas")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParadaDTO.class))),
                        @ApiResponse(responseCode = "204", description = "No se encontraron paradas")
        })
        @GetMapping
        public ParadaListDTO getAllParadas() {
                return ParadaListDTO.of(paradaService.findAll());
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
                        @Parameter(description = "Datos de la parada a crear", required = true) @RequestBody CreateUpdateParadaDTO paradaDTO) {
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

        @GetMapping("/buscar")
        public List<Parada> buscar(@RequestParam(value = "search", required = false) String search) {
                log.info(search);
                List<SearchCriteria> params = new ArrayList<SearchCriteria>();
                if (search != null) {
                        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
                        Matcher matcher = pattern.matcher(search + ",");
                        while (matcher.find()) {
                                log.info(matcher.group(1));
                                log.info(matcher.group(2));
                                log.info(matcher.group(3));
                                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                        }
                }
                return paradaService.search(params);
        }

}