// EstadoTrackingRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.EstadoTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoTrackingRepository extends JpaRepository<EstadoTracking, Long> {
    Optional<EstadoTracking> findByNombre(String nombre);
}
