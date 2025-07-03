package com.Techvault.servicioInventario.controller;

import com.Techvault.servicioInventario.model.Bodega;
import com.Techvault.servicioInventario.service.BodegaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BodegaController.class)
public class BodegaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BodegaService service;

    private final ObjectMapper mapper = new ObjectMapper();

    private Bodega bodega;

    @BeforeEach
    void setup() {
        bodega = new Bodega();
        bodega.setId(1L);
        bodega.setCiudad("Valparaíso");
        bodega.setDireccion("Av. Siempre Viva 742");
    }

    @Test
    void testGetAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(bodega));

        mockMvc.perform(get("/api/bodegas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ciudad").value("Valparaíso"));
    }

    @Test
    void testGetByIdFound() throws Exception {
        when(service.findById(1L)).thenReturn(bodega);

        mockMvc.perform(get("/api/bodegas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("Av. Siempre Viva 742"));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(service.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/bodegas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        when(service.save(any())).thenReturn(bodega);

        mockMvc.perform(post("/api/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bodega)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciudad").value("Valparaíso"));
    }

    @Test
    void testUpdateFound() throws Exception {
        when(service.findById(1L)).thenReturn(bodega);
        when(service.save(any())).thenReturn(bodega);

        Bodega updated = new Bodega();
        updated.setDireccion("Nueva Dirección");
        updated.setCiudad("Antofagasta");

        mockMvc.perform(put("/api/bodegas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ciudad").value("Antofagasta"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(service.findById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/bodegas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bodega)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteFound() throws Exception {
        when(service.findById(1L)).thenReturn(bodega);
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/api/bodegas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        when(service.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/bodegas/1"))
                .andExpect(status().isNotFound());
    }
}
