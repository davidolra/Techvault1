package com.Techvault.servicioPago.controller;

import com.Techvault.servicioPago.model.Cliente;
import com.Techvault.servicioPago.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.save(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente updatedCliente) {
        Cliente existing = clienteService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setNombre(updatedCliente.getNombre());
        existing.setRut(updatedCliente.getRut());
        existing.setEmail(updatedCliente.getEmail());

        return ResponseEntity.ok(clienteService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) return ResponseEntity.notFound().build();
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
