package com.Techvault.servicioPago.service.impl;

import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.repository.FacturaRepository;
import com.Techvault.servicioPago.service.FacturaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaServiceImpl(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Override
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura findById(Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        return factura.orElse(null);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }
}
