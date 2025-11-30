// DepartamentoRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
    // El ID es String (cod_dep)
}
