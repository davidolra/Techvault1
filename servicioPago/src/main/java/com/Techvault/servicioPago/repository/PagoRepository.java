package com.Techvault.servicioPago.repository;

import com.Techvault.servicioPago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
