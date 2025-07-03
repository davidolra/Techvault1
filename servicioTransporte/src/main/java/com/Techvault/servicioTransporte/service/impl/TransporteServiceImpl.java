package com.Techvault.servicioTransporte.service.impl;

import com.Techvault.servicioTransporte.model.Transporte;
import com.Techvault.servicioTransporte.repository.TransporteRepository;
import com.Techvault.servicioTransporte.service.TransporteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransporteServiceImpl implements TransporteService {

    private final TransporteRepository transporteRepository;

    public TransporteServiceImpl(TransporteRepository transporteRepository) {
        this.transporteRepository = transporteRepository;
    }

    @Override
    public List<Transporte> findAll() {
        return transporteRepository.findAll();
    }

    @Override
    public Transporte findById(Long id) {
        Optional<Transporte> transporte = transporteRepository.findById(id);
        return transporte.orElse(null);
    }

    @Override
    public Transporte save(Transporte transporte) {
        return transporteRepository.save(transporte);
    }

    @Override
    public void deleteById(Long id) {
        transporteRepository.deleteById(id);
    }
}
