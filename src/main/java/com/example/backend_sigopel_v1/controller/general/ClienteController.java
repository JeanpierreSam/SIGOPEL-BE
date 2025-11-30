package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.ClienteDTO;
import com.example.backend_sigopel_v1.service.general.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Listar todos los clientes.
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listAll() throws ServiceException {
        List<ClienteDTO> clientes = clienteService.listAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Ver detalle de un cliente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> read(@PathVariable Long id) throws ServiceException {
        ClienteDTO dto = clienteService.read(id);
        return ResponseEntity.ok(dto);
    }

    // === Métodos CRUD ===
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO clienteDTO) throws ServiceException {
        ClienteDTO created = clienteService.create(clienteDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) throws ServiceException {
        ClienteDTO updated = clienteService.update(id, clienteDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
