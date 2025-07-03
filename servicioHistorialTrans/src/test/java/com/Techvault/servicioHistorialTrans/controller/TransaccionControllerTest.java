package com.Techvault.servicioHistorialTrans.controller;

import com.Techvault.servicioHistorialTrans.model.Transaccion;
import com.Techvault.servicioHistorialTrans.service.TransaccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransaccionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransaccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaccionService service;

    // Registra JavaTimeModule para soportar LocalDateTime en JSON
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Transaccion transaccion;

    @BeforeEach
    void setUp() {
        transaccion = new Transaccion();
        transaccion.setId(1L);
        transaccion.setTipo("Pago");
        transaccion.setDescripcion("Descripcion");
        transaccion.setMonto(1000.0);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setIdReferencia(100L);
        transaccion.setServicioOrigen("Pago");
    }

    @Test
    void testGetAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(transaccion));

        mockMvc.perform(get("/api/transacciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("Pago"));
    }

    @Test
    void testGetById_Found() throws Exception {
        when(service.findById(1L)).thenReturn(transaccion);

        mockMvc.perform(get("/api/transacciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(service.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/transacciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        when(service.save(any(Transaccion.class))).thenReturn(transaccion);

        mockMvc.perform(post("/api/transacciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaccion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate_Found() throws Exception {
        when(service.findById(1L)).thenReturn(transaccion);
        when(service.save(any(Transaccion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaccion updated = new Transaccion();
        updated.setTipo("Reserva");
        updated.setDescripcion("Actualizado");
        updated.setMonto(2000.0);
        updated.setIdReferencia(101L);
        updated.setServicioOrigen("Reserva");

        mockMvc.perform(put("/api/transacciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Reserva")) // Aquí corregido a "Reserva"
                .andExpect(jsonPath("$.descripcion").value("Actualizado"))
                .andExpect(jsonPath("$.monto").value(2000.0))
                .andExpect(jsonPath("$.idReferencia").value(101))
                .andExpect(jsonPath("$.servicioOrigen").value("Reserva"));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        when(service.findById(99L)).thenReturn(null);

        mockMvc.perform(put("/api/transacciones/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaccion)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/api/transacciones/1"))
                .andExpect(status().isNoContent());
    }
}
