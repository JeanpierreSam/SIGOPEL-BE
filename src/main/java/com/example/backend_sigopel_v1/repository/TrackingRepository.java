// TrackingRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.dto.DetalleDTO;
import com.example.backend_sigopel_v1.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    List<Tracking> findAllByOrderByFechaServicioDesc();

    List<Tracking> findByFechaServicio(LocalDate fecha);

    boolean existsByFechaServicio(LocalDate fecha);



    @Query("SELECT t FROM Tracking t LEFT JOIN FETCH t.detalles WHERE t.id = :id")
    Optional<Tracking> findByIdWithDetalles(@Param("id") Long id);
}
