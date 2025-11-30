package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.*;
import com.example.backend_sigopel_v1.entity.*;
import com.example.backend_sigopel_v1.mapper.TrackingMapper;
import com.example.backend_sigopel_v1.repository.*;
import com.example.backend_sigopel_v1.service.general.service.ExcelImportService;
import com.example.backend_sigopel_v1.service.general.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Map;



@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final TrackingRepository trackingRepository;
    private final TrackingMapper trackingMapper;
    private final ExcelImportService excelImportService;

    // Repositorios necesarios para confirmar tracking
    private final TiendaRepository tiendaRepository;
    private final AlmacenRepository almacenRepository;
    private final EstadoEntregaRepository estadoEntregaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ChoferRepository choferRepository;
    private final EstadoTrackingRepository estadoTrackingRepository;



    @Override
    @Transactional
    public TrackingDTO create(TrackingDTO dto) throws ServiceException {
        try {
            if (dto.getClienteId() == null) {
                throw new ServiceException("Debe seleccionar un cliente");
            }
            Tracking entity = trackingMapper.toEntity(dto);
            Tracking saved  = trackingRepository.save(entity);
            return trackingMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error al crear tracking", e);
            throw new ServiceException("Error al crear tracking", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TrackingDTO read(Long id) throws ServiceException {
        try {
            Tracking entity = trackingRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tracking con id " + id + " no encontrado"));
            return trackingMapper.toDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al leer tracking {}", id, e);
            throw new ServiceException("Error al leer tracking con id " + id, e);
        }
    }

    @Override
    @Transactional
    public TrackingDTO update(Long id, TrackingDTO dto) throws ServiceException {
        try {
            Tracking entity = trackingRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tracking con id " + id + " no encontrado"));
            entity.setFechaServicio(dto.getFechaServicio());
            Tracking updated = trackingRepository.save(entity);
            return trackingMapper.toDTO(updated);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar tracking {}", id, e);
            throw new ServiceException("Error al actualizar tracking con id " + id, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            if (!trackingRepository.existsById(id)) {
                throw new ResourceNotFoundException("Tracking con id " + id + " no encontrado");
            }
            trackingRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar tracking {}", id, e);
            throw new ServiceException("Error al eliminar tracking con id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackingDTO> listAll() throws ServiceException {
        try {
            List<Tracking> list = trackingRepository.findAll();
            return trackingMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar trackings", e);
            throw new ServiceException("Error al listar todos los trackings", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackingDTO> listarOrdenadoPorFecha() throws ServiceException {
        try {
            List<Tracking> list = trackingRepository.findAllByOrderByFechaServicioDesc();
            return trackingMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al listar trackings ordenados", e);
            throw new ServiceException("Error al listar trackings ordenados por fecha", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackingDTO> buscarPorFecha(LocalDate fecha) throws ServiceException {
        try {
            List<Tracking> list = trackingRepository.findByFechaServicio(fecha);
            return trackingMapper.toDTOList(list);
        } catch (Exception e) {
            log.error("Error al buscar trackings por fecha {}", fecha, e);
            throw new ServiceException("Error al buscar trackings por fecha", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorFecha(LocalDate fecha) throws ServiceException {
        try {
            return trackingRepository.existsByFechaServicio(fecha);
        } catch (Exception e) {
            log.error("Error al verificar existencia de tracking para fecha {}", fecha, e);
            throw new ServiceException("Error al verificar existencia de tracking por fecha", e);
        }
    }

    // ===== MÉTODOS PARA EXCEL =====

    @Override
    public ProgramacionExcelDTO importarProgramacion(MultipartFile archivo) throws Exception {
        return excelImportService.importarProgramacion(archivo);
    }

    @Override
    @Transactional
    public TrackingDTO confirmarTracking(Long trackingId, ProgramacionExcelDTO programacion) throws Exception {
        try {
            Tracking tracking = trackingRepository.findById(trackingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tracking no encontrado"));

            EstadoEntrega estadoPendiente = estadoEntregaRepository.findByNombre("Pendiente")
                    .orElseThrow(() -> new RuntimeException("Estado 'Pendiente' no encontrado en BD"));

            // Limpiar detalles previos (si existen)
            tracking.getDetalles().clear();

            // Crear detalles desde programación importada
            for (ProgramacionExcelDTO.FilaProgramacion fila : programacion.getFilas()) {

                // Buscar y validar vehículo
                Vehiculo vehiculo = vehiculoRepository.findByPlaca(fila.getVehiculoPlaca())
                        .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con placa: " + fila.getVehiculoPlaca()));

                // Validar que el vehículo esté disponible
                if (!"Disponible".equals(vehiculo.getEstadoVehiculo().getTipo_estado())) {
                    throw new RuntimeException("Vehículo " + fila.getVehiculoPlaca() + " no está disponible (estado: " +
                            vehiculo.getEstadoVehiculo().getTipo_estado() + ")");
                }

                // Buscar tienda
                Tienda tienda = tiendaRepository.findByRazonSocial(fila.getTiendaRazonSocial())
                        .orElseThrow(() -> new RuntimeException("Tienda no encontrada: " + fila.getTiendaRazonSocial()));

                // Buscar almacén
                Almacen almacen = almacenRepository.findByNombre(fila.getAlmacenNombre())
                        .orElseThrow(() -> new RuntimeException("Almacén no encontrado: " + fila.getAlmacenNombre()));

                // Normalizar horas para que quepan en VARCHAR2(8)
                String vhInicio = normalizarHora(fila.getVentanaHorariaInicio());
                String vhFin    = normalizarHora(fila.getVentanaHorariaFin());

                // Crear detalle
                Detalle detalle = Detalle.builder()
                        .tracking(tracking)
                        .vehiculo(vehiculo)
                        .tienda(tienda)
                        .almacen(almacen)
                        .estadoEntrega(estadoPendiente)
                        .ventanaHorariaInicio(vhInicio)
                        .ventanaHorariaFin(vhFin)
                        .volumen(fila.getVolumen())
                        .observacion(fila.getObservacion())
                        .build();

                tracking.getDetalles().add(detalle);
            }

            EstadoTracking estadoSinAsignar = estadoTrackingRepository.findByNombre("Sin Asignar")
                    .orElseThrow(() -> new RuntimeException("Estado 'Sin Asignar' no encontrado"));

            tracking.setEstadoTracking(estadoSinAsignar);

            Tracking saved = trackingRepository.save(tracking);

            log.info("Tracking {} confirmado con {} detalles", trackingId, saved.getDetalles().size());

            return trackingMapper.toDTO(saved);

        } catch (RuntimeException e) {
            log.error("Error de validación al confirmar tracking {}: {}", trackingId, e.getMessage());
            throw new Exception("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al confirmar tracking {}", trackingId, e);
            throw new Exception("Error al confirmar tracking: " + e.getMessage());
        }
    }

    // === Normalización de horas para evitar ORA-12899 ===
    private String normalizarHora(String valor) {
        if (valor == null) {
            return null;
        }
        valor = valor.trim();

        // Si viene con fecha tipo 2025-11-28T08:00:00 -> dejar solo la parte de la hora
        int tIndex = valor.indexOf('T');
        if (tIndex >= 0 && valor.length() > tIndex + 1) {
            valor = valor.substring(tIndex + 1); // queda 08:00:00
        }

        // Si viene con segundos 08:00:00 -> 08:00
        if (valor.length() >= 5 && valor.charAt(2) == ':') {
            valor = valor.substring(0, 5);
        }

        // Seguridad extra: nunca superar 8 caracteres (columna VARCHAR2(8))
        return valor.length() > 8 ? valor.substring(0, 8) : valor;
    }

    @Override
    @Transactional
    public TrackingDTO asignarConductores(Long trackingId, AsignacionConductorDTO asignaciones) throws Exception {
        try {
            // 1. Buscar tracking
            Tracking tracking = trackingRepository.findById(trackingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tracking no encontrado"));

            // 2. Validar que no haya conductores repetidos
            List<Long> choferIds = asignaciones.getAsignaciones().stream()
                    .map(AsignacionConductorDTO.AsignacionVehiculoConductor::getChoferId)
                    .toList();

            Set<Long> choferIdsUnicos = new HashSet<>(choferIds);
            if (choferIds.size() != choferIdsUnicos.size()) {
                throw new RuntimeException("No se puede asignar el mismo conductor a varios vehículos");
            }

            // 3. Asignar chofer a cada detalle del tracking según la placa del vehículo
            for (AsignacionConductorDTO.AsignacionVehiculoConductor asignacion : asignaciones.getAsignaciones()) {

                // Buscar chofer
                Chofer chofer = choferRepository.findById(asignacion.getChoferId())
                        .orElseThrow(() -> new RuntimeException("Chofer no encontrado con id: " + asignacion.getChoferId()));

                // Validar que el chofer esté disponible
                String estadoChofer = chofer.getEstadoGeneral().getNombre();

                if (!"Disponible".equalsIgnoreCase(estadoChofer) &&
                        !"Activo".equalsIgnoreCase(estadoChofer)) {
                    throw new RuntimeException("Chofer " + chofer.getNombre()
                            + " no está disponible (estado: " + estadoChofer + ")");
                }


                // Asignar chofer a todos los detalles que usen ese vehículo
                List<Detalle> detallesConEseVehiculo = tracking.getDetalles().stream()
                        .filter(d -> d.getVehiculo().getPlaca().equals(asignacion.getVehiculoPlaca()))
                        .toList();

                if (detallesConEseVehiculo.isEmpty()) {
                    throw new RuntimeException("No se encontraron entregas para el vehículo: " + asignacion.getVehiculoPlaca());
                }

                for (Detalle detalle : detallesConEseVehiculo) {
                    detalle.setChofer(chofer);
                }
            }

            // Cambiar estado del tracking a "Pendiente" (o el que corresponda después de asignar)
            EstadoTracking estadoPendiente = estadoTrackingRepository.findByNombre("Pendiente")
                    .orElseThrow(() -> new RuntimeException("Estado 'Pendiente' no encontrado"));

            tracking.setEstadoTracking(estadoPendiente);

            Tracking saved = trackingRepository.save(tracking);
            log.info("Conductores asignados al tracking {}", trackingId);

            return trackingMapper.toDTO(saved);


        } catch (RuntimeException e) {
            log.error("Error de validación al asignar conductores al tracking {}: {}", trackingId, e.getMessage());
            throw new Exception("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al asignar conductores al tracking {}", trackingId, e);
            throw new Exception("Error al asignar conductores: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoEntregasDTO> obtenerVehiculosConEntregas(Long trackingId) throws Exception {
        try {
            Tracking tracking = trackingRepository.findById(trackingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tracking no encontrado"));

            // Agrupar detalles por vehículo
            Map<String, List<Detalle>> detallesPorVehiculo = tracking.getDetalles().stream()
                    .collect(Collectors.groupingBy(d -> d.getVehiculo().getPlaca()));

            return detallesPorVehiculo.entrySet().stream()
                    .map(entry -> {
                        String placa = entry.getKey();
                        List<Detalle> detalles = entry.getValue();
                        Detalle primerDetalle = detalles.getFirst();

                        return VehiculoEntregasDTO.builder()
                                .placa(placa)
                                .numeroEntregas(detalles.size())
                                .choferIdActual(primerDetalle.getChofer() != null ? primerDetalle.getChofer().getId() : null)
                                .choferNombreActual(primerDetalle.getChofer() != null ? primerDetalle.getChofer().getNombre() : null)
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al obtener vehículos con entregas del tracking {}", trackingId, e);
            throw new Exception("Error al obtener vehículos: " + e.getMessage());
        }


    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleDTO> listarEntregasPorTracking(Long trackingId) throws Exception {
        try {
            Tracking tracking = trackingRepository.findById(trackingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tracking no encontrado"));

            return tracking.getDetalles().stream()
                    .map(d -> DetalleDTO.builder()
                            .id(d.getId())
                            .vehiculoId(d.getVehiculo().getId())
                            .vehiculoPlaca(d.getVehiculo().getPlaca())
                            .choferId(d.getChofer() != null ? d.getChofer().getId() : null)
                            .choferNombre(d.getChofer() != null ? d.getChofer().getNombre() : null)
                            .tiendaId(d.getTienda().getId())
                            .tiendaRazonSocial(d.getTienda().getRazonSocial())
                            .tiendaDireccion(d.getTienda().getDireccion())
                            .almacenId(d.getAlmacen().getId())
                            .almacenNombre(d.getAlmacen().getNombre())
                            .estadoEntregaId(d.getEstadoEntrega().getId())
                            .estadoEntregaNombre(d.getEstadoEntrega().getNombre())
                            .ventanaHorariaInicio(d.getVentanaHorariaInicio())
                            .ventanaHorariaFin(d.getVentanaHorariaFin())
                            .observacion(d.getObservacion())
                            .volumen(d.getVolumen())
                            .build())
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar entregas del tracking {}", trackingId, e);
            throw new Exception("Error al listar entregas: " + e.getMessage());
        }
    }


}
