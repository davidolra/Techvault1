package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Cliente;
import com.Techvault.servicioContratacion.service.ClienteService;
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
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
    }

    @Test
    void testGetAll() throws Exception {
        when(clienteService.findAll()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"));
    }

    @Test
    void testGetById_Found() throws Exception {
        when(clienteService.findById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
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
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate_Found() throws Exception {
        when(clienteService.findById(1L)).thenReturn(cliente);
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updated = new Cliente();
        updated.setNombre("Pedro Gomez");
        updated.setRut("98765432-1");
        updated.setEmail("pedro@example.com");

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro Gomez"));
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
