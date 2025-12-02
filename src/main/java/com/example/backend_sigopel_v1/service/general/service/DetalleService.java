package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.CambioEstadoEntregaDTO;
import com.example.backend_sigopel_v1.dto.DetalleDTO;
import com.example.backend_sigopel_v1.entity.Detalle;
import com.example.backend_sigopel_v1.service.base.GenericService;


import java.util.List;

public interface DetalleService extends GenericService<Detalle, DetalleDTO, Long> {

    // Buscar detalles de un tracking específico
    List<DetalleDTO> buscarPorTrackingId(Long trackingId) throws org.hibernate.service.spi.ServiceException;
    DetalleDTO cambiarEstado(Long detalleId, CambioEstadoEntregaDTO dto) throws org.hibernate.service.spi.ServiceException;
}

