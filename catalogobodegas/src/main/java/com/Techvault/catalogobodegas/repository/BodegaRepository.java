package com.Techvault.catalogobodegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Techvault.catalogobodegas.model.Bodega;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}
