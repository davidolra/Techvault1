package com.Techvault.servicioInventario.service;

import com.Techvault.servicioInventario.model.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> findAll();
    Inventario findById(Long id);
    Inventario save(Inventario inventario);
    void deleteById(Long id);
}

