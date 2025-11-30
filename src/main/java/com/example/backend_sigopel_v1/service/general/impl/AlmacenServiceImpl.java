package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.AlmacenDTO;
import com.example.backend_sigopel_v1.entity.Almacen;
import com.example.backend_sigopel_v1.mapper.AlmacenMapper;
import com.example.backend_sigopel_v1.repository.AlmacenRepository;
import com.example.backend_sigopel_v1.service.general.service.AlmacenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlmacenServiceImpl implements AlmacenService {

    private final AlmacenRepository almacenRepository;
    private final AlmacenMapper almacenMapper;

    @Override
    @Transactional
    public AlmacenDTO create(AlmacenDTO dto) throws ServiceException {
        try {
            Almacen entity = almacenMapper.toEntity(dto);
            Almacen saved = almacenRepository.save(entity);
            return almacenMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error al crear almacén", e);
            throw new ServiceException("Error al crear almacén", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AlmacenDTO read(Long id) throws ServiceException {
        try {
            Almacen entity = almacenRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Almacén con id " + id + " no encontrado"));
            return almacenMapper.toDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al leer almacén {}", id, e);
            throw new ServiceException("Error al leer almacén con id " + id, e);
        }
    }

    @Override
    @Transactional
    public AlmacenDTO update(Long id, AlmacenDTO dto) throws ServiceException {
        try {
            Almacen entity = almacenRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Almacén con id " + id + " no encontrado"));
            entity.setNombre(dto.getNombre());
            Almacen updated = almacenRepository.save(entity);
            return almacenMapper.toDTO(updated);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar almacén {}", id, e);
            throw new ServiceException("Error al actualizar almacén con id " + id, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            if (!almacenRepository.existsById(id)) {
                throw new ResourceNotFoundException("Almacén con id " + id + " no encontrado");
            }
            almacenRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar almacén {}", id, e);
            throw new ServiceException("Error al eliminar almacén con id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlmacenDTO> listAll() throws ServiceException {
        try {
            List<Almacen> list = almacenRepository.findAll();
            return almacenMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar almacenes", e);
            throw new ServiceException("Error al listar todos los almacenes", e);
        }
    }
}
