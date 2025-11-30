    // DetalleRepository.java
    package com.example.backend_sigopel_v1.repository;
    
    import com.example.backend_sigopel_v1.entity.Detalle;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    
    import java.util.List;
    
    @Repository
    public interface DetalleRepository extends JpaRepository<Detalle, Long> {
        List<Detalle> findByTrackingId(Long trackingId);
    }
