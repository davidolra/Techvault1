package com.Techvault.servicioTransporte.controller;

import com.Techvault.servicioTransporte.model.Transporte;
import com.Techvault.servicioTransporte.service.TransporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportes")
public class TransporteController {

    private final TransporteService transporteService;

    public TransporteController(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    @GetMapping
    public List<Transporte> getAll() {
        return transporteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transporte> getById(@PathVariable Long id) {
        Transporte transporte = transporteService.findById(id);
        if (transporte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(transporte);
    }

    @PostMapping
    public ResponseEntity<Transporte> create(@RequestBody Transporte transporte) {
        Transporte nuevo = transporteService.save(transporte);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transporte> update(@PathVariable Long id, @RequestBody Transporte transporteActualizado) {
        Transporte existente = transporteService.findById(id);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setTipoTransporte(transporteActualizado.getTipoTransporte());
        existente.setEstado(transporteActualizado.getEstado());
        existente.setFechaHoraProgramada(transporteActualizado.getFechaHoraProgramada());
        existente.setDireccionOrigen(transporteActualizado.getDireccionOrigen());
        existente.setDireccionDestino(transporteActualizado.getDireccionDestino());
        existente.setResponsable(transporteActualizado.getResponsable());

        Transporte actualizado = transporteService.save(existente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Transporte transporte = transporteService.findById(id);
        if (transporte == null) return ResponseEntity.notFound().build();

        transporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
