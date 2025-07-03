package com.Techvault.servicioContratacion.service;

import com.Techvault.servicioContratacion.model.Contrato;
import java.util.List;

public interface ContratoService {
    List<Contrato> findAll();
    Contrato findById(Long id);
    Contrato save(Contrato contrato);
    void deleteById(Long id);
}
