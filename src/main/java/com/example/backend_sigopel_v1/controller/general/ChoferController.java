package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.ChoferDTO;
import com.example.backend_sigopel_v1.service.general.service.ChoferService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/choferes")
@CrossOrigin(origins = "http://localhost:4200")
public class ChoferController {

    private final ChoferService choferService;

    /**
     * Listar todos los choferes.
     */
    @GetMapping
    public ResponseEntity<List<ChoferDTO>> listAll() throws ServiceException {
        List<ChoferDTO> choferes = choferService.listAll();
        return ResponseEntity.ok(choferes);
    }

    /**
     * Ver detalle de un chofer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChoferDTO> read(@PathVariable Long id) throws ServiceException {
        ChoferDTO dto = choferService.read(id);
        return ResponseEntity.ok(dto);
    }

    // === Métodos CRUD ===
    @PostMapping
    public ResponseEntity<ChoferDTO> create(@RequestBody ChoferDTO choferDTO) throws ServiceException {
        ChoferDTO created = choferService.create(choferDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChoferDTO> update(@PathVariable Long id, @RequestBody ChoferDTO choferDTO) throws ServiceException {
        ChoferDTO updated = choferService.update(id, choferDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        choferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
