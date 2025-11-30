// DistritoRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, String> {
    // El ID es String (cod_dist)
}
