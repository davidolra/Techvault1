package com.Techvault.servicioContratacion.service.impl;

import com.Techvault.servicioContratacion.model.Contrato;
import com.Techvault.servicioContratacion.repository.ContratoRepository;
import com.Techvault.servicioContratacion.service.ContratoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImpl implements ContratoService {

    private final ContratoRepository contratoRepository;

    public ContratoServiceImpl(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @Override
    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    @Override
    public Contrato findById(Long id) {
        Optional<Contrato> contrato = contratoRepository.findById(id);
        return contrato.orElse(null);
    }

    @Override
    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    @Override
    public void deleteById(Long id) {
        contratoRepository.deleteById(id);
    }
}
