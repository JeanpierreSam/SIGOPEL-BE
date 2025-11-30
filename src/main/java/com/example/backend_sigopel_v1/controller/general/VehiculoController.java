// src/main/java/com/example/backend_sigopel_v1/controller/general/VehiculoController.java
package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.VehiculoDTO;
import com.example.backend_sigopel_v1.service.general.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    /**
     * RF01: Listar todos los vehículos registrados.
     * RF02: Buscar vehículos por capacidad, fecha de vencimiento SOAT o estado.
     */
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listAll(
            @RequestParam(required = false) BigDecimal capacidad,
            @RequestParam(required = false) String estadoNombre,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSoat
    ) throws ServiceException {
        List<VehiculoDTO> vehiculos = vehiculoService.buscarVehiculosConFiltros(capacidad, estadoNombre, fechaSoat);
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * RF03: Ver el detalle completo de un vehículo seleccionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> read(@PathVariable Long id) throws ServiceException {
        VehiculoDTO dto = vehiculoService.read(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * RF04: Exportar la lista filtrada de vehículos a formato PDF.
     */
    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportToPdf(
            @RequestParam(required = false) BigDecimal capacidad,
            @RequestParam(required = false) String estadoNombre,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSoat
    ) throws ServiceException {
        byte[] pdfBytes = vehiculoService.exportarVehiculosAPdf(capacidad, estadoNombre, fechaSoat);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=reporte_vehiculos.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    // === Métodos CRUD (opcionales para el CUS01) ===
    @PostMapping
    public ResponseEntity<VehiculoDTO> create(@RequestBody VehiculoDTO vehiculoDTO) throws ServiceException {
        VehiculoDTO created = vehiculoService.create(vehiculoDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoDTO> update(@PathVariable Long id, @RequestBody VehiculoDTO vehiculoDTO) throws ServiceException {
        VehiculoDTO updated = vehiculoService.update(id, vehiculoDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        vehiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}