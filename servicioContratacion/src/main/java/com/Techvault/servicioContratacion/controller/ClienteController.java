package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Cliente;
import com.Techvault.servicioContratacion.service.ClienteService;
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
        System.out.println("Se recibió cliente: " + cliente);
        return ResponseEntity.ok(clienteService.save(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente newCliente) {
        Cliente existing = clienteService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setNombre(newCliente.getNombre());
        existing.setRut(newCliente.getRut());
        existing.setEmail(newCliente.getEmail());

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
