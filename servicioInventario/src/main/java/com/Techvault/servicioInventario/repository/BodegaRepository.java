package com.Techvault.servicioInventario.repository;

import com.Techvault.servicioInventario.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {
}
