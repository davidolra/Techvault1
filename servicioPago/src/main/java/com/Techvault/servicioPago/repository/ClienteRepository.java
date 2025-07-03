package com.Techvault.servicioPago.repository;

import com.Techvault.servicioPago.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
