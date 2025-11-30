// EstadoGeneralRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.EstadoGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoGeneralRepository extends JpaRepository<EstadoGeneral, Long> {
    Optional<EstadoGeneral> findByNombre(String nombre);
}
