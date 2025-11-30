package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.CostoAdicionalDTO;
import com.example.backend_sigopel_v1.dto.TipoCostoDTO;

import java.util.List;

public interface CostoAdicionalService {

    List<TipoCostoDTO> listarTiposCosto() throws Exception;

    List<CostoAdicionalDTO> listarPorDetalle(Long detalleId) throws Exception;

    CostoAdicionalDTO registrarCosto(Long detalleId, CostoAdicionalDTO dto) throws Exception;
}
