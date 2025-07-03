package com.Techvault.servicioInventario.controller;

import com.Techvault.servicioInventario.controller.ClienteController;
import com.Techvault.servicioInventario.model.Cliente;
import com.Techvault.servicioInventario.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Pedro Perez");
        cliente.setEmail("pedro@example.com");
    }

    @Test
    void testGetAll() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Pedro Perez"));
    }

    @Test
    void testGetById_Found() throws Exception {
        when(clienteService.findById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("pedro@example.com"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(clienteService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro Perez"));
    }

    @Test
    void testUpdate_Found() throws Exception {
        Long id = 1L;

        Cliente existente = new Cliente();
        existente.setId(id);
        existente.setNombre("Pedro Perez");
        existente.setEmail("pedro@example.com");

        Cliente actualizado = new Cliente();
        actualizado.setId(id);
        actualizado.setNombre("Pedro Actualizado");
        actualizado.setEmail("actualizado@example.com");

        when(clienteService.findById(id)).thenReturn(existente);
        when(clienteService.save(any(Cliente.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Pedro Actualizado")) // ✅ CORREGIDO
                .andExpect(jsonPath("$.email").value("actualizado@example.com")); // ✅ CORREGIDO
    }


    @Test
    void testUpdate_NotFound() throws Exception {
        when(clienteService.findById(99L)).thenReturn(null);

        mockMvc.perform(put("/api/clientes/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_Found() throws Exception {
        when(clienteService.findById(1L)).thenReturn(cliente);
        doNothing().when(clienteService).deleteById(1L);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(clienteService.findById(99L)).thenReturn(null);

        mockMvc.perform(delete("/api/clientes/99"))
                .andExpect(status().isNotFound());
    }
}