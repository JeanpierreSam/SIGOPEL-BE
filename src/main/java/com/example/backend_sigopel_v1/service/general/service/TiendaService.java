package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.TiendaDTO;
import com.example.backend_sigopel_v1.entity.Tienda;
import com.example.backend_sigopel_v1.service.base.GenericService;

import java.util.Optional;

public interface TiendaService extends GenericService<Tienda, TiendaDTO, Long> {

    // RF08: buscar tienda por razón social para validar si existe
    Optional<TiendaDTO> buscarPorRazonSocial(String razonSocial) throws org.hibernate.service.spi.ServiceException;
}
