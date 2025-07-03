
package com.Techvault.servicioHistorialTrans.controller;



import com.Techvault.servicioHistorialTrans.model.Transaccion;
import com.Techvault.servicioHistorialTrans.service.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaccion> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> getById(@PathVariable Long id) {
        Transaccion transaccion = service.findById(id);
        return transaccion != null ? ResponseEntity.ok(transaccion) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Transaccion> create(@RequestBody Transaccion transaccion) {
        transaccion.setFecha(LocalDateTime.now());
        return ResponseEntity.ok(service.save(transaccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> update(@PathVariable Long id, @RequestBody Transaccion nueva) {
        Transaccion existente = service.findById(id);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setTipo(nueva.getTipo());
        existente.setMonto(nueva.getMonto());
        existente.setFecha(LocalDateTime.now());
        existente.setIdReferencia(nueva.getIdReferencia());
        existente.setServicioOrigen(nueva.getServicioOrigen());
        existente.setDescripcion(nueva.getDescripcion());

        return ResponseEntity.ok(service.save(existente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}