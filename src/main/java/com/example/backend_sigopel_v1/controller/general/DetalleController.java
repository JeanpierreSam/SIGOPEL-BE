package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.CambioEstadoEntregaDTO;
import com.example.backend_sigopel_v1.dto.DetalleDTO;
import com.example.backend_sigopel_v1.service.general.service.DetalleService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/detalles")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleController {

    private final DetalleService detalleService;

    /**
     * Listar todos los detalles.
     */
    @GetMapping
    public ResponseEntity<List<DetalleDTO>> listAll() throws ServiceException {
        List<DetalleDTO> detalles = detalleService.listAll();
        return ResponseEntity.ok(detalles);
    }

    /**
     * Buscar detalles por ID de tracking.
     */
    @GetMapping("/tracking/{trackingId}")
    public ResponseEntity<List<DetalleDTO>> buscarPorTrackingId(@PathVariable Long trackingId) throws ServiceException {
        List<DetalleDTO> detalles = detalleService.buscarPorTrackingId(trackingId);
        return ResponseEntity.ok(detalles);
    }

    /**
     * Ver detalle específico.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetalleDTO> read(@PathVariable Long id) throws ServiceException {
        DetalleDTO dto = detalleService.read(id);
        return ResponseEntity.ok(dto);
    }

    // === Métodos CRUD ===
    @PostMapping
    public ResponseEntity<DetalleDTO> create(@RequestBody DetalleDTO detalleDTO) throws ServiceException {
        DetalleDTO created = detalleService.create(detalleDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleDTO> update(@PathVariable Long id, @RequestBody DetalleDTO detalleDTO) throws ServiceException {
        DetalleDTO updated = detalleService.update(id, detalleDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        detalleService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/estado")

    public ResponseEntity<DetalleDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestBody CambioEstadoEntregaDTO request) throws ServiceException {

        DetalleDTO actualizado = detalleService.cambiarEstado(id, request);
        return ResponseEntity.ok(actualizado);
    }
}
