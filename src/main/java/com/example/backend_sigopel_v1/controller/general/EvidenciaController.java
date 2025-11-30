package com.example.backend_sigopel_v1.controller.general;

import com.example.backend_sigopel_v1.dto.EvidenciaDTO;
import com.example.backend_sigopel_v1.service.general.service.EvidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evidencias")
@RequiredArgsConstructor
public class EvidenciaController {

    private final EvidenciaService evidenciaService;

    @GetMapping("/detalle/{detalleId}")
    public ResponseEntity<List<EvidenciaDTO>> listarPorDetalle(@PathVariable Long detalleId) throws Exception {
        return ResponseEntity.ok(evidenciaService.listarPorDetalle(detalleId));
    }

    @PostMapping("/detalle/{detalleId}")
    public ResponseEntity<EvidenciaDTO> registrar(
            @PathVariable Long detalleId,
            @RequestPart("archivo") MultipartFile archivo,
            @RequestPart("tipoEvidencia") String tipoEvidencia,
            @RequestPart("descripcion") String descripcion
    ) throws Exception {
        return ResponseEntity.ok(
                evidenciaService.registrarEvidencia(detalleId, tipoEvidencia, descripcion, archivo)
        );
    }
}
