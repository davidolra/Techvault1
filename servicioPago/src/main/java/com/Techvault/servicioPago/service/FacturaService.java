package com.Techvault.servicioPago.service;

import com.Techvault.servicioPago.model.Factura;

import java.util.List;

public interface FacturaService {
    List<Factura> findAll();
    Factura findById(Long id);
    Factura save(Factura factura);
    void deleteById(Long id);
}
