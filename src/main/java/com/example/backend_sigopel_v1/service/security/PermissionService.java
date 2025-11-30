package com.example.backend_sigopel_v1.service.security;

import com.example.backend_sigopel_v1.entity.security.Usuario;
import com.example.backend_sigopel_v1.repository.security.GrantedPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private GrantedPermissionRepository grantedPermissionRepository;

    public List<String> getOperationNamesByUser(Usuario user) {
        return grantedPermissionRepository.findByRolId(user.getRol().getId())
                .stream()
                .map(gp -> gp.getOperacion().getNombre())
                .toList();
    }
}
