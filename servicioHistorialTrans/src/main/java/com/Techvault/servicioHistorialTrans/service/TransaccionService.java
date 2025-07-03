
package com.Techvault.servicioHistorialTrans.service;



import com.Techvault.servicioHistorialTrans.model.Transaccion;

import java.util.List;

public interface TransaccionService {
    List<Transaccion> findAll();
    Transaccion findById(Long id);
    Transaccion save(Transaccion transaccion);
    void deleteById(Long id);
}

