
package com.Techvault.servicioHistorialTrans.service.impl;


import com.Techvault.servicioHistorialTrans.model.Transaccion;
import com.Techvault.servicioHistorialTrans.repository.TransaccionRepository;
import com.Techvault.servicioHistorialTrans.service.TransaccionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository repository;

    public TransaccionServiceImpl(TransaccionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaccion> findAll() {
        return repository.findAll();
    }

    @Override
    public Transaccion findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Transaccion save(Transaccion transaccion) {
        return repository.save(transaccion);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}