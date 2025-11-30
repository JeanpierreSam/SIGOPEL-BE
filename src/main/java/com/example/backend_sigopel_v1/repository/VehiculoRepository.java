package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Buscar vehículo por placa (para importación Excel)
    Optional<Vehiculo> findByPlaca(String placa);

    /**
     * Busca vehículos aplicando filtros de capacidad mínima, nombre de estado y fecha de vencimiento de SOAT.
     */
    @Query("SELECT DISTINCT v FROM Vehiculo v " +
            "LEFT JOIN VehiculoSoat vs ON v.id = vs.vehiculo.id " +
            "WHERE " +
            "(:capacidad IS NULL OR v.capacidad >= :capacidad) AND " +
            "(:estadoNombre IS NULL OR v.estadoVehiculo.tipo_estado = :estadoNombre) AND " +
            "(:fechaSoat IS NULL OR vs.fecha_fin >= :fechaSoat) " +
            "ORDER BY v.id")
    List<Vehiculo> findVehiculosConFiltros(
            @Param("capacidad") BigDecimal capacidad,
            @Param("estadoNombre") String estadoNombre,
            @Param("fechaSoat") LocalDate fechaSoat
    );
}
