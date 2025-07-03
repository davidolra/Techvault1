package com.Techvault.servicioPago.service;

import com.Techvault.servicioPago.model.Pago;

import java.util.List;

public interface PagoService {
    List<Pago> findAll();
    Pago findById(Long id);
    Pago save(Pago pago);
    void deleteById(Long id);
}
