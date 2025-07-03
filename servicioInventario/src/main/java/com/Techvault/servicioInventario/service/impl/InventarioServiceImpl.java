package com.Techvault.servicioInventario.service.impl;

import com.Techvault.servicioInventario.model.Inventario;
import com.Techvault.servicioInventario.repository.InventarioRepository;
import com.Techvault.servicioInventario.service.InventarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    @Override
    public Inventario findById(Long id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);
        return inventario.orElse(null);
    }

    @Override
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public void deleteById(Long id) {
        inventarioRepository.deleteById(id);
    }
}
