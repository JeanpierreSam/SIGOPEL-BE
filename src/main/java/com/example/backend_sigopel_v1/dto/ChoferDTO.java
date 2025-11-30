package com.example.backend_sigopel_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoferDTO {
    private Long id;
    private String nombre;
    private String dni;
    private String telefono;
    private Long estadoGeneralId;
    private String estadoGeneralNombre;
}
