package com.Techvault.servicioTransporte.service;

import com.Techvault.servicioTransporte.model.Transporte;

import java.util.List;

public interface TransporteService {
    List<Transporte> findAll();
    Transporte findById(Long id);
    Transporte save(Transporte transporte);
    void deleteById(Long id);
}
