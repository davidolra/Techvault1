package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Bodega;
import com.Techvault.servicioContratacion.service.BodegaService;
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

@WebMvcTest(BodegaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BodegaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BodegaService bodegaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Bodega bodega;

    @BeforeEach
    void setUp() {
        bodega = new Bodega();
        bodega.setId(1L);
        bodega.setUbicacion("Calle Falsa 123");
        bodega.setTamaño(50.0);
        bodega.setPrecio(150000.0);
    }

    @Test
    void testGetAll() throws Exception {
        when(bodegaService.findAll()).thenReturn(List.of(bodega));

        mockMvc.perform(get("/api/bodegas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ubicacion").value("Calle Falsa 123"));
    }

    @Test
    void testGetById_Found() throws Exception {
        when(bodegaService.findById(1L)).thenReturn(bodega);

        mockMvc.perform(get("/api/bodegas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(bodegaService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/bodegas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        when(bodegaService.save(any(Bodega.class))).thenReturn(bodega);

        mockMvc.perform(post("/api/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodega)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate_Found() throws Exception {
        when(bodegaService.findById(1L)).thenReturn(bodega);
        when(bodegaService.save(any(Bodega.class))).thenReturn(bodega);

        Bodega updated = new Bodega();
        updated.setUbicacion("Nueva Direccion");
        updated.setTamaño(60.0);
        updated.setPrecio(180000.0);

        mockMvc.perform(put("/api/bodegas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ubicacion").value("Nueva Direccion"));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        when(bodegaService.findById(99L)).thenReturn(null);

        mockMvc.perform(put("/api/bodegas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodega)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_Found() throws Exception {
        when(bodegaService.findById(1L)).thenReturn(bodega);
        doNothing().when(bodegaService).deleteById(1L);

        mockMvc.perform(delete("/api/bodegas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(bodegaService.findById(99L)).thenReturn(null);

        mockMvc.perform(delete("/api/bodegas/99"))
                .andExpect(status().isNotFound());
    }
}
