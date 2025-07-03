package com.Techvault.servicioInventario.controller;

import com.Techvault.servicioInventario.model.Inventario;
import com.Techvault.servicioInventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public List<Inventario> getAll() {
        return inventarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getById(@PathVariable Long id) {
        Inventario inventario = inventarioService.findById(id);
        return inventario != null ? ResponseEntity.ok(inventario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Inventario> create(@RequestBody Inventario inventario) {
        return ResponseEntity.ok(inventarioService.save(inventario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> update(@PathVariable Long id, @RequestBody Inventario newInventario) {
        Inventario existing = inventarioService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setDescripcion(newInventario.getDescripcion());
        existing.setCantidad(newInventario.getCantidad());
        existing.setFechaRegistro(newInventario.getFechaRegistro());
        existing.setCliente(newInventario.getCliente());
        existing.setBodega(newInventario.getBodega());

        return ResponseEntity.ok(inventarioService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Inventario inventario = inventarioService.findById(id);
        if (inventario == null) return ResponseEntity.notFound().build();
        inventarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
