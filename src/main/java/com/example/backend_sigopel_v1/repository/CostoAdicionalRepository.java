package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.CostoAdicional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostoAdicionalRepository extends JpaRepository<CostoAdicional, Long> {

    List<CostoAdicional> findByDetalleId(Long detalleId);
}
