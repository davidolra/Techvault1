package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Contrato;
import com.Techvault.servicioContratacion.model.Cliente;
import com.Techvault.servicioContratacion.model.Bodega;
import com.Techvault.servicioContratacion.service.ContratoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;  // <--- import agregado
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContratoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratoService contratoService;

    // Se registra JavaTimeModule para manejar LocalDate en JSON
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Contrato contrato;
    private Cliente cliente;
    private Bodega bodega;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");

        bodega = new Bodega();
        bodega.setId(1L);
        bodega.setUbicacion("Calle 1");
        bodega.setTamaño(100.0);
        bodega.setPrecio(200000.0);

        contrato = new Contrato();
        contrato.setId(1L);
        contrato.setCliente(cliente);
        contrato.setBodega(bodega);
        contrato.setFechaInicio(LocalDate.now());
        contrato.setFechaFin(LocalDate.now().plusMonths(6));
        contrato.setMontoTotal(1200000.0);
        contrato.setEstado("Activo");
    }

    @Test
    void testGetAllContratos() throws Exception {
        when(contratoService.findAll()).thenReturn(List.of(contrato));

        mockMvc.perform(get("/api/contratos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("Activo"));
    }

    @Test
    void testGetContratoById_Found() throws Exception {
        when(contratoService.findById(1L)).thenReturn(contrato);

        mockMvc.perform(get("/api/contratos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetContratoById_NotFound() throws Exception {
        when(contratoService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/contratos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateContrato() throws Exception {
        when(contratoService.save(any(Contrato.class))).thenReturn(contrato);

        mockMvc.perform(post("/api/contratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contrato)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateContrato_Found() throws Exception {
        when(contratoService.findById(1L)).thenReturn(contrato);
        when(contratoService.save(any(Contrato.class))).thenReturn(contrato);

        Contrato updated = new Contrato();
        updated.setCliente(cliente);
        updated.setBodega(bodega);
        updated.setFechaInicio(LocalDate.now());
        updated.setFechaFin(LocalDate.now().plusMonths(12));
        updated.setMontoTotal(2400000.0);
        updated.setEstado("Finalizado");

        mockMvc.perform(put("/api/contratos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Finalizado"));
    }

    @Test
    void testUpdateContrato_NotFound() throws Exception {
        when(contratoService.findById(99L)).thenReturn(null);

        mockMvc.perform(put("/api/contratos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contrato)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteContrato_Found() throws Exception {
        when(contratoService.findById(1L)).thenReturn(contrato);
        doNothing().when(contratoService).deleteById(1L);

        mockMvc.perform(delete("/api/contratos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteContrato_NotFound() throws Exception {
        when(contratoService.findById(99L)).thenReturn(null);

        mockMvc.perform(delete("/api/contratos/99"))
                .andExpect(status().isNotFound());
    }
}
