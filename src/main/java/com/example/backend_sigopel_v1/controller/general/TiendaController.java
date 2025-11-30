package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.TiendaDTO;
import com.example.backend_sigopel_v1.service.general.service.TiendaService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tiendas")
@CrossOrigin(origins = "http://localhost:4200")
public class TiendaController {

    private final TiendaService tiendaService;

    /**
     * Listar todas las tiendas.
     */
    @GetMapping
    public ResponseEntity<List<TiendaDTO>> listAll() throws ServiceException {
        List<TiendaDTO> tiendas = tiendaService.listAll();
        return ResponseEntity.ok(tiendas);
    }

    /**
     * RF08: Buscar tienda por razón social (para validar si existe).
     */
    @GetMapping("/buscar")
    public ResponseEntity<TiendaDTO> buscarPorRazonSocial(@RequestParam String razonSocial) throws ServiceException {
        Optional<TiendaDTO> tienda = tiendaService.buscarPorRazonSocial(razonSocial);
        return tienda.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Ver detalle de una tienda.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TiendaDTO> read(@PathVariable Long id) throws ServiceException {
        TiendaDTO dto = tiendaService.read(id);
        return ResponseEntity.ok(dto);
    }

    // === Métodos CRUD ===
    @PostMapping
    public ResponseEntity<TiendaDTO> create(@RequestBody TiendaDTO tiendaDTO) throws ServiceException {
        TiendaDTO created = tiendaService.create(tiendaDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiendaDTO> update(@PathVariable Long id, @RequestBody TiendaDTO tiendaDTO) throws ServiceException {
        TiendaDTO updated = tiendaService.update(id, tiendaDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        tiendaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
