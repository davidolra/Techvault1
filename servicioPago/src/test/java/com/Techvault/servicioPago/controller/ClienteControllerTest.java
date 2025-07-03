package com.Techvault.servicioPago.controller;

import com.Techvault.servicioPago.model.Cliente;
import com.Techvault.servicioPago.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        List<Cliente> lista = Collections.singletonList(cliente);

        when(clienteService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"));
    }

    @Test
    void testGetById_Found() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");

        when(clienteService.findById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(clienteService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1L);
        clienteGuardado.setNombre(cliente.getNombre());
        clienteGuardado.setRut(cliente.getRut());
        clienteGuardado.setEmail(cliente.getEmail());

        when(clienteService.save(any(Cliente.class))).thenReturn(clienteGuardado);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void testUpdate_Found() throws Exception {
        Cliente existente = new Cliente();
        existente.setId(1L);
        existente.setNombre("Juan Perez");
        existente.setRut("12345678-9");
        existente.setEmail("juan@example.com");

        Cliente actualizado = new Cliente();
        actualizado.setNombre("Juan Actualizado");
        actualizado.setRut("98765432-1");
        actualizado.setEmail("juan.actualizado@example.com");

        Cliente guardado = new Cliente();
        guardado.setId(1L);
        guardado.setNombre(actualizado.getNombre());
        guardado.setRut(actualizado.getRut());
        guardado.setEmail(actualizado.getEmail());

        when(clienteService.findById(1L)).thenReturn(existente);
        when(clienteService.save(any(Cliente.class))).thenReturn(guardado);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"))
                .andExpect(jsonPath("$.rut").value("98765432-1"));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        when(clienteService.findById(1L)).thenReturn(null);

        Cliente actualizado = new Cliente();
        actualizado.setNombre("Juan Actualizado");

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_Found() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteService.findById(1L)).thenReturn(cliente);
        doNothing().when(clienteService).deleteById(1L);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(clienteService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNotFound());
    }
}
