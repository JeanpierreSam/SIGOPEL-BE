// AlmacenRepository.java
package com.example.backend_sigopel_v1.repository;

import com.example.backend_sigopel_v1.entity.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    Optional<Almacen> findByNombre(String nombre);
}
