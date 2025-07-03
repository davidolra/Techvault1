package com.Techvault.servicioContratacion.service;

import com.Techvault.servicioContratacion.model.Bodega;
import java.util.List;

public interface BodegaService {
    List<Bodega> findAll();
    Bodega findById(Long id);
    Bodega save(Bodega bodega);
    void deleteById(Long id);
}
