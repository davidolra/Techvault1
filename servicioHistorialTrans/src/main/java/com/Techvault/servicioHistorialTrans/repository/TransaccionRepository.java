package com.Techvault.servicioHistorialTrans.repository;

import com.Techvault.servicioHistorialTrans.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
