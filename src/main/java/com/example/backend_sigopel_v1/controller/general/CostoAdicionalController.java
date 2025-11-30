package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.CostoAdicionalDTO;
import com.example.backend_sigopel_v1.dto.TipoCostoDTO;
import com.example.backend_sigopel_v1.service.general.service.CostoAdicionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/costos-adicionales")
@RequiredArgsConstructor
public class CostoAdicionalController {

    private final CostoAdicionalService costoAdicionalService;

    // Para llenar el combo de tipos de gasto
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoCostoDTO>> listarTipos() throws Exception {
        return ResponseEntity.ok(costoAdicionalService.listarTiposCosto());
    }

    // Listar costos de una entrega específica
    @GetMapping("/detalle/{detalleId}")
    public ResponseEntity<List<CostoAdicionalDTO>> listarPorDetalle(@PathVariable Long detalleId) throws Exception {
        return ResponseEntity.ok(costoAdicionalService.listarPorDetalle(detalleId));
    }

    // Registrar costo adicional para una entrega finalizada
    @PostMapping("/detalle/{detalleId}")
    public ResponseEntity<CostoAdicionalDTO> registrar(
            @PathVariable Long detalleId,
            @RequestBody CostoAdicionalDTO dto
    ) throws Exception {
        return ResponseEntity.ok(costoAdicionalService.registrarCosto(detalleId, dto));
    }
}
