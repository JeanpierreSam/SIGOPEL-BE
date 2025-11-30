package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.TiendaDTO;
import com.example.backend_sigopel_v1.entity.Tienda;
import com.example.backend_sigopel_v1.mapper.TiendaMapper;
import com.example.backend_sigopel_v1.repository.TiendaRepository;
import com.example.backend_sigopel_v1.service.general.service.TiendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TiendaServiceImpl implements TiendaService {

    private final TiendaRepository tiendaRepository;
    private final TiendaMapper tiendaMapper;

    @Override
    @Transactional
    public TiendaDTO create(TiendaDTO dto) throws ServiceException {
        try {
            Tienda entity = tiendaMapper.toEntity(dto);
            Tienda saved = tiendaRepository.save(entity);
            return tiendaMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error al crear tienda", e);
            throw new ServiceException("Error al crear tienda", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TiendaDTO read(Long id) throws ServiceException {
        try {
            Tienda entity = tiendaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tienda con id " + id + " no encontrada"));
            return tiendaMapper.toDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al leer tienda {}", id, e);
            throw new ServiceException("Error al leer tienda con id " + id, e);
        }
    }

    @Override
    @Transactional
    public TiendaDTO update(Long id, TiendaDTO dto) throws ServiceException {
        try {
            Tienda entity = tiendaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tienda con id " + id + " no encontrada"));

            entity.setRazonSocial(dto.getRazonSocial());
            entity.setDireccion(dto.getDireccion());
            // distrito se setea en otra lógica si es necesario

            Tienda updated = tiendaRepository.save(entity);
            return tiendaMapper.toDTO(updated);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar tienda {}", id, e);
            throw new ServiceException("Error al actualizar tienda con id " + id, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            if (!tiendaRepository.existsById(id)) {
                throw new ResourceNotFoundException("Tienda con id " + id + " no encontrada");
            }
            tiendaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar tienda {}", id, e);
            throw new ServiceException("Error al eliminar tienda con id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiendaDTO> listAll() throws ServiceException {
        try {
            List<Tienda> list = tiendaRepository.findAll();
            return tiendaMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar tiendas", e);
            throw new ServiceException("Error al listar todas las tiendas", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TiendaDTO> buscarPorRazonSocial(String razonSocial) throws ServiceException {
        try {
            return tiendaRepository.findByRazonSocial(razonSocial)
                    .map(tiendaMapper::toDTO);
        } catch (Exception e) {
            log.error("Error al buscar tienda por razón social {}", razonSocial, e);
            throw new ServiceException("Error al buscar tienda por razón social", e);
        }
    }
}
