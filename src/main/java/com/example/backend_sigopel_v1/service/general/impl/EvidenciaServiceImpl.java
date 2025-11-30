package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.EvidenciaDTO;
import com.example.backend_sigopel_v1.entity.Detalle;
import com.example.backend_sigopel_v1.entity.Evidencia;
import com.example.backend_sigopel_v1.repository.DetalleRepository;
import com.example.backend_sigopel_v1.repository.EvidenciaRepository;
import com.example.backend_sigopel_v1.service.general.service.EvidenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvidenciaServiceImpl implements EvidenciaService {

    private final EvidenciaRepository evidenciaRepository;
    private final DetalleRepository detalleRepository;

    private final String basePath = "evidencias"; // carpeta en el servidor

    @Override
    @Transactional(readOnly = true)
    public List<EvidenciaDTO> listarPorDetalle(Long detalleId) throws Exception {
        return evidenciaRepository.findByDetalleId(detalleId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public EvidenciaDTO registrarEvidencia(Long detalleId,
                                           String tipoEvidencia,
                                           String descripcion,
                                           MultipartFile archivo) throws Exception {
        try {
            if (archivo == null || archivo.isEmpty()) {
                throw new RuntimeException("Debe adjuntar un archivo de evidencia");
            }

            Detalle detalle = detalleRepository.findById(detalleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Entrega no encontrada"));

            Files.createDirectories(Paths.get(basePath));
            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path destino = Paths.get(basePath).resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), destino);

            Evidencia evidencia = Evidencia.builder()
                    .detalle(detalle)
                    .tipoEvidencia(tipoEvidencia)
                    .descripcion(descripcion)
                    .rutaArchivo(destino.toString())
                    .fechaRegistro(LocalDateTime.now())
                    .build();

            Evidencia saved = evidenciaRepository.save(evidencia);
            return toDTO(saved);

        } catch (RuntimeException e) {
            log.error("Error de validación al registrar evidencia para detalle {}: {}", detalleId, e.getMessage());
            throw new Exception("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al registrar evidencia para detalle {}", detalleId, e);
            throw new Exception("Error al registrar evidencia: " + e.getMessage());
        }
    }

    private EvidenciaDTO toDTO(Evidencia e) {
        return EvidenciaDTO.builder()
                .id(e.getId())
                .detalleId(e.getDetalle().getId())
                .tipoEvidencia(e.getTipoEvidencia())
                .rutaArchivo(e.getRutaArchivo())
                .descripcion(e.getDescripcion())
                .build();
    }
}
