// src/main/java/com/example/backend_sigopel_v1/service/general/impl/VehiculoServiceImpl.java
package com.example.backend_sigopel_v1.service.general.impl;

import com.example.backend_sigopel_v1.controller.error.ResourceNotFoundException;
import com.example.backend_sigopel_v1.dto.VehiculoDTO;
import com.example.backend_sigopel_v1.entity.Vehiculo;
import com.example.backend_sigopel_v1.mapper.VehiculoMapper;
import com.example.backend_sigopel_v1.repository.VehiculoRepository;
import com.example.backend_sigopel_v1.service.general.service.PdfExportService;
import com.example.backend_sigopel_v1.service.general.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;
    private final PdfExportService pdfExportService; // Nueva dependencia

    @Override
    public VehiculoDTO create(VehiculoDTO vehiculoDTO) throws ServiceException {
        try {
            Vehiculo vehiculo = vehiculoMapper.toEntity(vehiculoDTO);
            Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
            return vehiculoMapper.toDTO(vehiculoGuardado);
        } catch (Exception e) {
            log.error("Error al crear el vehículo", e);
            throw new ServiceException("Error al crear el vehículo", e);
        }
    }

    @Override
    public VehiculoDTO read(Long id) throws ServiceException {
        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehículo con id " + id + " no encontrado"));
            return vehiculoMapper.toDTO(vehiculo);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error al leer el vehículo con id " + id, e);
        }
    }

    @Override
    public VehiculoDTO update(Long id, VehiculoDTO vehiculoDTO) throws ServiceException {
        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehículo con id " + id + " no encontrado"));
            vehiculo.setPlaca(vehiculoDTO.getPlaca());
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setCapacidad(vehiculoDTO.getCapacidad());
            // Nota: El estadoVehiculo se manejaría por separado si se actualiza

            Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculo);
            return vehiculoMapper.toDTO(vehiculoActualizado);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar el vehículo con id " + id, e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            if (!vehiculoRepository.existsById(id)) {
                throw new ResourceNotFoundException("Vehículo con id " + id + " no encontrado");
            }
            vehiculoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar el vehículo con id " + id, e);
        }
    }

    @Override
    public List<VehiculoDTO> listAll() throws ServiceException {
        try {
            List<Vehiculo> vehiculos = vehiculoRepository.findAll();
            return vehiculoMapper.toDTOList(vehiculos);
        } catch (Exception e) {
            throw new ServiceException("Error al listar todos los vehículos", e);
        }
    }

    @Override
    public List<VehiculoDTO> buscarVehiculosConFiltros(BigDecimal capacidad, String estadoNombre, LocalDate fechaSoat) {
        try {
            List<Vehiculo> vehiculos = vehiculoRepository.findVehiculosConFiltros(capacidad, estadoNombre, fechaSoat);
            return vehiculos.stream()
                    .map(vehiculoMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al buscar vehículos con filtros", e);
            throw new ServiceException("Error al buscar vehículos con filtros: " + e.getMessage());
        }
    }

    // === MÉTODO NUEVO PARA RF04 ===
    @Override
    public byte[] exportarVehiculosAPdf(BigDecimal capacidad, String estadoNombre, LocalDate fechaSoat) {
        try {
            List<VehiculoDTO> vehiculos = this.buscarVehiculosConFiltros(capacidad, estadoNombre, fechaSoat);
            return pdfExportService.exportVehiculosToPdf(vehiculos);
        } catch (Exception e) {
            log.error("Error al exportar vehículos a PDF", e);
            throw new ServiceException("Error al exportar el reporte de vehículos a PDF", e);
        }
    }
}