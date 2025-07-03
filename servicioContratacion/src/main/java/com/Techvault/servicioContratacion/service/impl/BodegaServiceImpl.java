package com.Techvault.servicioContratacion.service.impl;

import com.Techvault.servicioContratacion.model.Bodega;
import com.Techvault.servicioContratacion.repository.BodegaRepository;
import com.Techvault.servicioContratacion.service.BodegaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaServiceImpl implements BodegaService {

    private final BodegaRepository bodegaRepository;

    public BodegaServiceImpl(BodegaRepository bodegaRepository) {
        this.bodegaRepository = bodegaRepository;
    }

    @Override
    public List<Bodega> findAll() {
        return bodegaRepository.findAll();
    }

    @Override
    public Bodega findById(Long id) {
        Optional<Bodega> bodega = bodegaRepository.findById(id);
        return bodega.orElse(null);
    }

    @Override
    public Bodega save(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    @Override
    public void deleteById(Long id) {
        bodegaRepository.deleteById(id);
    }
}
