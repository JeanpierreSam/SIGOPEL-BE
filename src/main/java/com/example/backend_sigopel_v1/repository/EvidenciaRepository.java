package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.Evidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {
    List<Evidencia> findByDetalleId(Long detalleId);
}