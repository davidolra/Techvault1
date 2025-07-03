package com.Techvault.servicioPago.controller;

import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.model.Pago;
import com.Techvault.servicioPago.service.FacturaService;
import com.Techvault.servicioPago.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;
    private final FacturaService facturaService;

    public PagoController(PagoService pagoService, FacturaService facturaService) {
        this.pagoService = pagoService;
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<Pago> getAll() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getById(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        return pago != null ? ResponseEntity.ok(pago) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Pago pago) {
        if (pago.getFactura() == null || pago.getFactura().getId() == null) {
            return ResponseEntity.badRequest().body("Factura no especificada o inválida");
        }

        Factura factura = facturaService.findById(pago.getFactura().getId());
        if (factura == null) {
            return ResponseEntity.badRequest().body("Factura con id " + pago.getFactura().getId() + " no existe");
        }

        pago.setFactura(factura);
        Pago nuevoPago = pagoService.save(pago);
        return ResponseEntity.ok(nuevoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Pago newPago) {
        Pago existing = pagoService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        if (newPago.getFactura() == null || newPago.getFactura().getId() == null) {
            return ResponseEntity.badRequest().body("Factura no especificada o inválida");
        }

        Factura factura = facturaService.findById(newPago.getFactura().getId());
        if (factura == null) {
            return ResponseEntity.badRequest().body("Factura con id " + newPago.getFactura().getId() + " no existe");
        }

        existing.setFechaPago(newPago.getFechaPago());
        existing.setMonto(newPago.getMonto());
        existing.setFactura(factura);

        Pago pagoGuardado = pagoService.save(existing);
        return ResponseEntity.ok(pagoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        if (pago == null) return ResponseEntity.notFound().build();
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
