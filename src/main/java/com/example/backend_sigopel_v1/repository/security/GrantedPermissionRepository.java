package com.example.backend_sigopel_v1.repository.security;

import com.example.backend_sigopel_v1.entity.security.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrantedPermissionRepository extends JpaRepository<GrantedPermission, Long> {
    List<GrantedPermission> findByRolId(Long rolId);
}
