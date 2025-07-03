package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Contrato;
import com.Techvault.servicioContratacion.service.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public List<Contrato> getAll() {
        return contratoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> getById(@PathVariable Long id) {
        Contrato contrato = contratoService.findById(id);
        return (contrato != null) ? ResponseEntity.ok(contrato) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contrato> create(@RequestBody Contrato contrato) {
        return ResponseEntity.ok(contratoService.save(contrato));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrato> update(@PathVariable Long id, @RequestBody Contrato newContrato) {
        Contrato existing = contratoService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setCliente(newContrato.getCliente());
        existing.setBodega(newContrato.getBodega());
        existing.setFechaInicio(newContrato.getFechaInicio());
        existing.setFechaFin(newContrato.getFechaFin());
        existing.setMontoTotal(newContrato.getMontoTotal());
        existing.setEstado(newContrato.getEstado());

        return ResponseEntity.ok(contratoService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Contrato contrato = contratoService.findById(id);
        if (contrato == null) return ResponseEntity.notFound().build();
        contratoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
