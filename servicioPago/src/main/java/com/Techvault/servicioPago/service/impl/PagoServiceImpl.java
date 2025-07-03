package com.Techvault.servicioPago.service.impl;

import com.Techvault.servicioPago.model.Pago;
import com.Techvault.servicioPago.repository.PagoRepository;
import com.Techvault.servicioPago.service.PagoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago findById(Long id) {
        Optional<Pago> pago = pagoRepository.findById(id);
        return pago.orElse(null);
    }

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }
}
