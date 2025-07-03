package com.Techvault.servicioContratacion.service;

import com.Techvault.servicioContratacion.model.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente findById(Long id);
    Cliente save(Cliente cliente);
    void deleteById(Long id);
}
