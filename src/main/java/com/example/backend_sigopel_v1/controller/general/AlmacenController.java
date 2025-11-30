package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.AlmacenDTO;
import com.example.backend_sigopel_v1.service.general.service.AlmacenService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/almacenes")
@CrossOrigin(origins = "http://localhost:4200")
public class AlmacenController {

    private final AlmacenService almacenService;

    /**
     * Listar todos los almacenes.
     */
    @GetMapping
    public ResponseEntity<List<AlmacenDTO>> listAll() throws ServiceException {
        List<AlmacenDTO> almacenes = almacenService.listAll();
        return ResponseEntity.ok(almacenes);
    }

    /**
     * Ver detalle de un almacén.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlmacenDTO> read(@PathVariable Long id) throws ServiceException {
        AlmacenDTO dto = almacenService.read(id);
        return ResponseEntity.ok(dto);
    }

    // === Métodos CRUD ===
    @PostMapping
    public ResponseEntity<AlmacenDTO> create(@RequestBody AlmacenDTO almacenDTO) throws ServiceException {
        AlmacenDTO created = almacenService.create(almacenDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenDTO> update(@PathVariable Long id, @RequestBody AlmacenDTO almacenDTO) throws ServiceException {
        AlmacenDTO updated = almacenService.update(id, almacenDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        almacenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
