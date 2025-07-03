package com.Techvault.catalogobodegas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Techvault.catalogobodegas.model.Bodega;
import com.Techvault.catalogobodegas.service.BodegaService;

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
        if (bodega == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bodega);
    }

    @PostMapping
    public Bodega create(@RequestBody Bodega bodega) {
        return bodegaService.save(bodega);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bodega> update(@PathVariable Long id, @RequestBody Bodega bodegaDetails) {
        Bodega bodega = bodegaService.findById(id);
        if (bodega == null) {
            return ResponseEntity.notFound().build();
        }

        bodega.setNombre(bodegaDetails.getNombre());
        bodega.setUbicacion(bodegaDetails.getUbicacion());
        bodega.setTamaño(bodegaDetails.getTamaño());
        bodega.setPrecio(bodegaDetails.getPrecio());
        bodega.setEstado(bodegaDetails.getEstado());
        bodega.setDescripcion(bodegaDetails.getDescripcion());

        Bodega updatedBodega = bodegaService.save(bodega);
        return ResponseEntity.ok(updatedBodega);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Bodega bodega = bodegaService.findById(id);
        if (bodega == null) {
            return ResponseEntity.notFound().build();
        }
        bodegaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
