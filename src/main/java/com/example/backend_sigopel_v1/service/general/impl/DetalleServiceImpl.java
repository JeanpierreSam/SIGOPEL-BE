package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.CambioEstadoEntregaDTO;
import com.example.backend_sigopel_v1.dto.DetalleDTO;
import com.example.backend_sigopel_v1.entity.Detalle;
import com.example.backend_sigopel_v1.entity.EstadoEntrega;
import com.example.backend_sigopel_v1.mapper.DetalleMapper;
import com.example.backend_sigopel_v1.repository.DetalleRepository;
import com.example.backend_sigopel_v1.repository.EstadoEntregaRepository;
import com.example.backend_sigopel_v1.service.general.service.DetalleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetalleServiceImpl implements DetalleService {

    private final DetalleRepository detalleRepository;
    private final DetalleMapper detalleMapper;
    private final EstadoEntregaRepository estadoEntregaRepository;

    @Override
    @Transactional
    public DetalleDTO create(DetalleDTO dto) throws ServiceException {
        try {
            Detalle entity = detalleMapper.toEntity(dto);
            Detalle saved = detalleRepository.save(entity);
            return detalleMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error al crear detalle", e);
            throw new ServiceException("Error al crear detalle", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleDTO read(Long id) throws ServiceException {
        try {
            Detalle entity = detalleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Detalle con id " + id + " no encontrado"));
            return detalleMapper.toDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al leer detalle {}", id, e);
            throw new ServiceException("Error al leer detalle con id " + id, e);
        }
    }

    @Override
    @Transactional
    public DetalleDTO update(Long id, DetalleDTO dto) throws ServiceException {
        try {
            Detalle entity = detalleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Detalle con id " + id + " no encontrado"));

            // Actualizas campos editables
            entity.setVentanaHorariaInicio(dto.getVentanaHorariaInicio());
            entity.setVentanaHorariaFin(dto.getVentanaHorariaFin());
            entity.setObservacion(dto.getObservacion());
            entity.setVolumen(dto.getVolumen());
            // Tracking, vehiculo, chofer, tienda, almacen, estadoEntrega se setean en otra lógica

            Detalle updated = detalleRepository.save(entity);
            return detalleMapper.toDTO(updated);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar detalle {}", id, e);
            throw new ServiceException("Error al actualizar detalle con id " + id, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            if (!detalleRepository.existsById(id)) {
                throw new ResourceNotFoundException("Detalle con id " + id + " no encontrado");
            }
            detalleRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar detalle {}", id, e);
            throw new ServiceException("Error al eliminar detalle con id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleDTO> listAll() throws ServiceException {
        try {
            List<Detalle> list = detalleRepository.findAll();
            return detalleMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar detalles", e);
            throw new ServiceException("Error al listar todos los detalles", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleDTO> buscarPorTrackingId(Long trackingId) throws ServiceException {
        try {
            List<Detalle> list = detalleRepository.findByTrackingId(trackingId);
            return detalleMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al buscar detalles por tracking {}", trackingId, e);
            throw new ServiceException("Error al buscar detalles por tracking", e);
        }
    }
    @Override
    public List<EstadoEntrega> obtenerEstados() throws ServiceException {
        try {
            return estadoEntregaRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener estados de entrega", e);
        }
    }
    @Override
    @Transactional
    public DetalleDTO cambiarEstado(Long detalleId, CambioEstadoEntregaDTO dto) throws ServiceException {
        try {
            // 1. Buscar el detalle
            Detalle detalle = detalleRepository.findById(detalleId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Detalle no encontrado con id: " + detalleId));

            // 2. Buscar el nuevo estado por ID (del DTO)
            EstadoEntrega nuevoEstado = estadoEntregaRepository.findById(dto.getEstadoEntregaId())
                    .orElseThrow(() -> new RuntimeException(
                            "Estado de entrega no encontrado con id: " + dto.getEstadoEntregaId()));

            // 3. Actualizar estado
            detalle.setEstadoEntrega(nuevoEstado);

            // 4. Actualizar observación si viene algo
            if (dto.getObservacion() != null && !dto.getObservacion().isBlank()) {
                detalle.setObservacion(dto.getObservacion());
            }

            // 5. Guardar
            Detalle guardado = detalleRepository.save(detalle);
            return detalleMapper.toDTO(guardado);

        } catch (RuntimeException e) {
            log.error("Error de validación al cambiar estado de entrega {}: {}", detalleId, e.getMessage());
            throw new ServiceException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error al cambiar estado de entrega {}", detalleId, e);
            throw new ServiceException("Error al cambiar estado de entrega", e);
        }
    }

}
