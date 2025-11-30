package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.ClienteDTO;
import com.example.backend_sigopel_v1.entity.Cliente;
import com.example.backend_sigopel_v1.mapper.ClienteMapper;
import com.example.backend_sigopel_v1.repository.ClienteRepository;
import com.example.backend_sigopel_v1.service.general.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteDTO create(ClienteDTO dto) throws ServiceException {
        try {
            Cliente entity = clienteMapper.toEntity(dto);
            Cliente saved = clienteRepository.save(entity);
            return clienteMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error al crear cliente", e);
            throw new ServiceException("Error al crear cliente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO read(Long id) throws ServiceException {
        try {
            Cliente entity = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no encontrado"));
            return clienteMapper.toDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al leer cliente {}", id, e);
            throw new ServiceException("Error al leer cliente con id " + id, e);
        }
    }

    @Override
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) throws ServiceException {
        try {
            Cliente entity = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no encontrado"));
            entity.setRuc(dto.getRuc());
            entity.setRazonSocial(dto.getRazonSocial());
            entity.setRepresentanteLegal(dto.getRepresentanteLegal());
            Cliente updated = clienteRepository.save(entity);
            return clienteMapper.toDTO(updated);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar cliente {}", id, e);
            throw new ServiceException("Error al actualizar cliente con id " + id, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            if (!clienteRepository.existsById(id)) {
                throw new ResourceNotFoundException("Cliente con id " + id + " no encontrado");
            }
            clienteRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar cliente {}", id, e);
            throw new ServiceException("Error al eliminar cliente con id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listAll() throws ServiceException {
        try {
            List<Cliente> list = clienteRepository.findAll();
            return clienteMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar clientes", e);
            throw new ServiceException("Error al listar todos los clientes", e);
        }
    }
}
