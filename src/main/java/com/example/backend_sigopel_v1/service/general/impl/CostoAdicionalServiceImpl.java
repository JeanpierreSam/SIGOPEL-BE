package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.CostoAdicionalDTO;
import com.example.backend_sigopel_v1.dto.TipoCostoDTO;
import com.example.backend_sigopel_v1.entity.CostoAdicional;
import com.example.backend_sigopel_v1.entity.Detalle;
import com.example.backend_sigopel_v1.entity.TipoCosto;
import com.example.backend_sigopel_v1.repository.CostoAdicionalRepository;
import com.example.backend_sigopel_v1.repository.DetalleRepository;
import com.example.backend_sigopel_v1.repository.TipoCostoRepository;
import com.example.backend_sigopel_v1.service.general.service.CostoAdicionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CostoAdicionalServiceImpl implements CostoAdicionalService {

    private final CostoAdicionalRepository costoAdicionalRepository;
    private final TipoCostoRepository tipoCostoRepository;
    private final DetalleRepository detalleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCostoDTO> listarTiposCosto() throws Exception {
        return tipoCostoRepository.findAll().stream()
                .map(tc -> new TipoCostoDTO(tc.getId(), tc.getNombre()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CostoAdicionalDTO> listarPorDetalle(Long detalleId) throws Exception {
        List<CostoAdicional> lista = costoAdicionalRepository.findByDetalleId(detalleId);
        return lista.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public CostoAdicionalDTO registrarCosto(Long detalleId, CostoAdicionalDTO dto) throws Exception {
        try {
            Detalle detalle = detalleRepository.findById(detalleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Entrega no encontrada"));

// Solo permitir gastos si la entrega está finalizada
            if (!"Finalizado".equalsIgnoreCase(detalle.getEstadoEntrega().getNombre())) {
                throw new RuntimeException("Solo se pueden registrar costos para entregas finalizadas");
            }

            TipoCosto tipo = tipoCostoRepository.findById(dto.getTipoCostoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo de costo no encontrado"));

            CostoAdicional entity = CostoAdicional.builder()
                    .detalle(detalle)
                    .tipoCosto(tipo)
                    .monto(dto.getMonto())
                    .descripcion(dto.getDescripcion())
                    .build();

            CostoAdicional saved = costoAdicionalRepository.save(entity);
            return toDTO(saved);

        } catch (RuntimeException e) {
            log.error("Error de validación al registrar costo adicional para detalle {}: {}", detalleId, e.getMessage());
            throw new Exception("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al registrar costo adicional para detalle {}", detalleId, e);
            throw new Exception("Error al registrar costo adicional: " + e.getMessage());
        }
    }

    private CostoAdicionalDTO toDTO(CostoAdicional c) {
        return CostoAdicionalDTO.builder()
                .id(c.getId())
                .detalleId(c.getDetalle().getId())
                .tipoCostoId(c.getTipoCosto().getId())
                .tipoCostoNombre(c.getTipoCosto().getNombre())
                .monto(c.getMonto())
                .descripcion(c.getDescripcion())
                .build();
    }
}
