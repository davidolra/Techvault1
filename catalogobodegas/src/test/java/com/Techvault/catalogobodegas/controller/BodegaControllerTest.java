package com.Techvault.catalogobodegas.controller;

import com.Techvault.catalogobodegas.model.Bodega;
import com.Techvault.catalogobodegas.service.BodegaService;
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
    private BodegaService bodegaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Bodega bodega;

    @BeforeEach
    void setUp() {
        bodega = new Bodega(1L, "Bodega Test", "Santiago", 100.0, 250000.0, "disponible", "Bodega para test");
    }

    @Test
    public void testGetAllBodegas() throws Exception {
        when(bodegaService.findAll()).thenReturn(List.of(bodega));

        mockMvc.perform(get("/api/bodegas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bodega.getId()))
                .andExpect(jsonPath("$[0].nombre").value(bodega.getNombre()));
    }

    @Test
    public void testGetBodegaById_Found() throws Exception {
        when(bodegaService.findById(1L)).thenReturn(bodega);

        mockMvc.perform(get("/api/bodegas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bodega.getId()))
                .andExpect(jsonPath("$.nombre").value(bodega.getNombre()));
    }

    @Test
    public void testGetBodegaById_NotFound() throws Exception {
        when(bodegaService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/bodegas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBodega() throws Exception {
        when(bodegaService.save(any(Bodega.class))).thenReturn(bodega);

        mockMvc.perform(post("/api/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodega)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bodega.getId()))
                .andExpect(jsonPath("$.nombre").value(bodega.getNombre()));
    }

    @Test
    public void testUpdateBodega_Found() throws Exception {
        Bodega updated = new Bodega(1L, "Bodega Actualizada", "Santiago", 120.0, 260000.0, "disponible", "Bodega actualizada");

        when(bodegaService.findById(1L)).thenReturn(bodega);
        when(bodegaService.save(any(Bodega.class))).thenReturn(updated);

        mockMvc.perform(put("/api/bodegas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Bodega Actualizada"))
                .andExpect(jsonPath("$.precio").value(260000.0));
    }

    @Test
    public void testUpdateBodega_NotFound() throws Exception {
        when(bodegaService.findById(99L)).thenReturn(null);

        mockMvc.perform(put("/api/bodegas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bodega)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBodega_Found() throws Exception {
        when(bodegaService.findById(1L)).thenReturn(bodega);
        doNothing().when(bodegaService).deleteById(1L);

        mockMvc.perform(delete("/api/bodegas/1"))
                .andExpect(status().isNoContent());

        verify(bodegaService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBodega_NotFound() throws Exception {
        when(bodegaService.findById(99L)).thenReturn(null);

        mockMvc.perform(delete("/api/bodegas/99"))
                .andExpect(status().isNotFound());
    }
}
