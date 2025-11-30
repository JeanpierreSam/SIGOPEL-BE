package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.*;
import com.example.backend_sigopel_v1.entity.Tracking;
import com.example.backend_sigopel_v1.service.base.GenericService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface TrackingService extends GenericService<Tracking, TrackingDTO, Long> {

    // RF01: Listar todas las hojas ordenadas por fecha
    List<TrackingDTO> listarOrdenadoPorFecha() throws org.hibernate.service.spi.ServiceException;

    // RF02: Buscar por fecha servicio
    List<TrackingDTO> buscarPorFecha(LocalDate fecha) throws org.hibernate.service.spi.ServiceException;

    // RF04: Verificar si ya existe hoja para una fecha
    boolean existePorFecha(LocalDate fecha) throws org.hibernate.service.spi.ServiceException;

    // RF05: Importar programación desde Excel
    ProgramacionExcelDTO importarProgramacion(MultipartFile archivo) throws Exception;

    // RF09: Confirmar tracking (convertir programación en detalles)
    TrackingDTO confirmarTracking(Long trackingId, ProgramacionExcelDTO programacion) throws Exception;

    TrackingDTO asignarConductores(Long trackingId, AsignacionConductorDTO asignaciones) throws Exception;

    List<VehiculoEntregasDTO> obtenerVehiculosConEntregas(Long trackingId) throws Exception;

    List<DetalleDTO> listarEntregasPorTracking(Long trackingId) throws Exception;
}
