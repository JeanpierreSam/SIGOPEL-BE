package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.EvidenciaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EvidenciaService {

    List<EvidenciaDTO> listarPorDetalle(Long detalleId) throws Exception;

    EvidenciaDTO registrarEvidencia(Long detalleId,
                                    String tipoEvidencia,
                                    String descripcion,
                                    MultipartFile archivo) throws Exception;
}
