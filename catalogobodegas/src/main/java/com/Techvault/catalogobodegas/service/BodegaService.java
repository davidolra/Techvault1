package com.Techvault.catalogobodegas.service;

import java.util.List;
import com.Techvault.catalogobodegas.model.Bodega;

public interface BodegaService {
    List<Bodega> findAll();
    Bodega findById(Long id);
    Bodega save(Bodega bodega);
    void deleteById(Long id);
}
