    package com.example.backend_sigopel_v1.dto;

    import lombok.*;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class TiendaDTO {
        private Long id;
        private String razonSocial;
        private String direccion;
        private String codigoDistrito;
        private String nombreDistrito;
    }
