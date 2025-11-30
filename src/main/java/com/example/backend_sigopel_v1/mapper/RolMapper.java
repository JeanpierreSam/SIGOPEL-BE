        package com.example.backend_sigopel_v1.mapper;

        import com.example.backend_sigopel_v1.dto.RolDTO;
        import com.example.backend_sigopel_v1.entity.security.Rol;
        import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
        import org.mapstruct.Mapper;

        @Mapper(componentModel = "spring")
        public interface RolMapper extends BaseMapper<Rol, RolDTO> {
        }
