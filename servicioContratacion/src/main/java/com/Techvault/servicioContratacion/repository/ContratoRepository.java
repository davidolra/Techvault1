package com.Techvault.servicioContratacion.repository;

import com.Techvault.servicioContratacion.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
