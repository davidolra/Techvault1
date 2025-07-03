package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Bodega;
import com.Techvault.servicioContratacion.service.BodegaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
public class BodegaController {

    private final BodegaService bodegaService;

    public BodegaController(BodegaService bodegaService) {
        this.bodegaService = bodegaService;
    }

    @GetMapping
    public List<Bodega> getAll() {
        return bodegaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bodega> getById(@PathVariable Long id) {
        Bodega bodega = bodegaService.findById(id);
        return bodega != null ? ResponseEntity.ok(bodega) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Bodega> create(@RequestBody Bodega bodega) {
        return ResponseEntity.ok(bodegaService.save(bodega));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bodega> update(@PathVariable Long id, @RequestBody Bodega newBodega) {
        Bodega existing = bodegaService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setUbicacion(newBodega.getUbicacion());
        existing.setTamaño(newBodega.getTamaño());
        existing.setPrecio(newBodega.getPrecio());

        return ResponseEntity.ok(bodegaService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Bodega bodega = bodegaService.findById(id);
        if (bodega == null) return ResponseEntity.notFound().build();
        bodegaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
