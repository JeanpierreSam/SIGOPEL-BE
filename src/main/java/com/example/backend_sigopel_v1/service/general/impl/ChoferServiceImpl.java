package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.ChoferDTO;
import com.example.backend_sigopel_v1.entity.Chofer;
import com.example.backend_sigopel_v1.mapper.ChoferMapper;
import com.example.backend_sigopel_v1.repository.ChoferRepository;
import com.example.backend_sigopel_v1.service.general.service.ChoferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements ChoferService {

    private final ChoferRepository choferRepository;
    private final ChoferMapper choferMapper;

    @Override
    @Transactional
    public ChoferDTO create(ChoferDTO dto) throws ServiceException {
        try {
            Chofer entity = choferMapper.toEntity(dto);
            Chofer saved = choferRepository.save(entity);
            return choferMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error al crear chofer", e);
            throw new ServiceException("Error al crear chofer", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ChoferDTO read(Long id) throws ServiceException {
        try {
            Chofer entity = choferRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Chofer con id " + id + " no encontrado"));
            return choferMapper.toDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al leer chofer {}", id, e);
            throw new ServiceException("Error al leer chofer con id " + id, e);
        }
    }

    @Override
    @Transactional
    public ChoferDTO update(Long id, ChoferDTO dto) throws ServiceException {
        try {
            Chofer entity = choferRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Chofer con id " + id + " no encontrado"));
            entity.setNombre(dto.getNombre());
            entity.setDni(dto.getDni());
            entity.setTelefono(dto.getTelefono());
            Chofer updated = choferRepository.save(entity);
            return choferMapper.toDTO(updated);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar chofer {}", id, e);
            throw new ServiceException("Error al actualizar chofer con id " + id, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            if (!choferRepository.existsById(id)) {
                throw new ResourceNotFoundException("Chofer con id " + id + " no encontrado");
            }
            choferRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar chofer {}", id, e);
            throw new ServiceException("Error al eliminar chofer con id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChoferDTO> listAll() throws ServiceException {
        try {
            List<Chofer> list = choferRepository.findAll();
            return choferMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar choferes", e);
            throw new ServiceException("Error al listar todos los choferes", e);
        }
    }
}
