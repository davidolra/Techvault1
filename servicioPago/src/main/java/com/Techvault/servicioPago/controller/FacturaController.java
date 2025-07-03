package com.Techvault.servicioPago.controller;

import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.model.Cliente;
import com.Techvault.servicioPago.service.ClienteService;
import com.Techvault.servicioPago.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final ClienteService clienteService;

    public FacturaController(FacturaService facturaService, ClienteService clienteService) {
        this.facturaService = facturaService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Factura> getAll() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getById(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);
        return factura != null ? ResponseEntity.ok(factura) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Factura factura) {
        if (factura.getCliente() == null || factura.getCliente().getId() == null) {
            return ResponseEntity.badRequest().body("Cliente no especificado o inválido");
        }

        Cliente cliente = clienteService.findById(factura.getCliente().getId());
        if (cliente == null) {
            return ResponseEntity.badRequest().body("Cliente con id " + factura.getCliente().getId() + " no existe");
        }

        factura.setCliente(cliente);
        return ResponseEntity.ok(facturaService.save(factura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Factura newFactura) {
        Factura existing = facturaService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        if (newFactura.getCliente() == null || newFactura.getCliente().getId() == null) {
            return ResponseEntity.badRequest().body("Cliente no especificado o inválido");
        }

        Cliente cliente = clienteService.findById(newFactura.getCliente().getId());
        if (cliente == null) {
            return ResponseEntity.badRequest().body("Cliente con id " + newFactura.getCliente().getId() + " no existe");
        }

        existing.setFechaEmision(newFactura.getFechaEmision());
        existing.setTotal(newFactura.getTotal());
        existing.setCliente(cliente);

        return ResponseEntity.ok(facturaService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);
        if (factura == null) return ResponseEntity.notFound().build();
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
