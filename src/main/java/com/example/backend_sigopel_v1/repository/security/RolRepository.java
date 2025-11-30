package com.example.backend_sigopel_v1.repository.security;

import com.example.backend_sigopel_v1.entity.security.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
