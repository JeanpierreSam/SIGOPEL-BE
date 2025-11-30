package com.example.backend_sigopel_v1.dto.security;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
