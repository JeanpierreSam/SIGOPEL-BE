package com.example.backend_sigopel_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String ruc;
    private String razonSocial;
    private String representanteLegal;
    private Long estadoGeneralId;
    private String estadoGeneralNombre;
}
