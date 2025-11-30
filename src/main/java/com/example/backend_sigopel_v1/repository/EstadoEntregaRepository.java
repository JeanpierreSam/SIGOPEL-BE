// EstadoEntregaRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.EstadoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoEntregaRepository extends JpaRepository<EstadoEntrega, Long> {
    Optional<EstadoEntrega> findByNombre(String nombre);
}
