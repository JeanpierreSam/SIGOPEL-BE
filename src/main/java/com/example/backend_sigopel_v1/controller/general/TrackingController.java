package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.*;
import com.example.backend_sigopel_v1.service.general.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tracking")
@CrossOrigin(origins = "http://localhost:4200")
public class TrackingController {

    private final TrackingService trackingService;

    /**
     * RF01: Listar todas las hojas de tracking ordenadas por fecha.
     */
    @GetMapping
    public ResponseEntity<List<TrackingDTO>> listAll() throws ServiceException {
        List<TrackingDTO> trackings = trackingService.listarOrdenadoPorFecha();
        return ResponseEntity.ok(trackings);
    }

    /**
     * RF02: Buscar hojas de tracking por fecha de servicio.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<TrackingDTO>> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) throws ServiceException {
        List<TrackingDTO> trackings = trackingService.buscarPorFecha(fecha);
        return ResponseEntity.ok(trackings);
    }

    /**
     * RF03: Ver el detalle completo de una hoja de tracking.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackingDTO> read(@PathVariable Long id) throws ServiceException {
        TrackingDTO dto = trackingService.read(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * RF04: Verificar si ya existe hoja de tracking para una fecha.
     */
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existePorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) throws ServiceException {
        boolean existe = trackingService.existePorFecha(fecha);
        return ResponseEntity.ok(existe);
    }

    // === Métodos CRUD ===
    @PostMapping
    public ResponseEntity<TrackingDTO> create(@RequestBody TrackingDTO trackingDTO) throws ServiceException {
        System.out.println("CREAR TRACKING DTO: clienteId=" + trackingDTO.getClienteId());
        TrackingDTO created = trackingService.create(trackingDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TrackingDTO> update(@PathVariable Long id, @RequestBody TrackingDTO trackingDTO) throws ServiceException {
        TrackingDTO updated = trackingService.update(id, trackingDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        trackingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/importar-excel")
    public ResponseEntity<ProgramacionExcelDTO> importarExcel(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo
    ) throws Exception {
        ProgramacionExcelDTO programacion = trackingService.importarProgramacion(archivo);
        return ResponseEntity.ok(programacion);
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<TrackingDTO> confirmarTracking(
            @PathVariable Long id,
            @RequestBody ProgramacionExcelDTO programacion
    ) throws Exception {
        TrackingDTO dto = trackingService.confirmarTracking(id, programacion);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/asignar-conductores")
    public ResponseEntity<TrackingDTO> asignarConductores(
            @PathVariable Long id,
            @RequestBody AsignacionConductorDTO asignaciones
    ) throws Exception {
        TrackingDTO dto = trackingService.asignarConductores(id, asignaciones);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{id}/vehiculos-entregas")
    public ResponseEntity<List<VehiculoEntregasDTO>> obtenerVehiculosConEntregas(@PathVariable Long id) throws Exception {
        List<VehiculoEntregasDTO> vehiculos = trackingService.obtenerVehiculosConEntregas(id);
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/{id}/entregas")
    public ResponseEntity<List<DetalleDTO>> listarEntregas(@PathVariable Long id) throws Exception {
        List<DetalleDTO> entregas = trackingService.listarEntregasPorTracking(id);
        return ResponseEntity.ok(entregas);
    }

}
