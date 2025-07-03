package com.Techvault.servicioPago.repository;

import com.Techvault.servicioPago.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
