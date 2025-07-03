package com.Techvault.servicioInventario.controller;

import com.Techvault.servicioInventario.model.Inventario;
import com.Techvault.servicioInventario.model.Cliente;
import com.Techvault.servicioInventario.model.Bodega;
import com.Techvault.servicioInventario.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Inventario i = new Inventario();
        i.setId(1L);
        i.setDescripcion("Producto X");
        i.setCantidad(10);
        i.setFechaRegistro(LocalDate.now());

        when(inventarioService.findAll()).thenReturn(Collections.singletonList(i));

        mockMvc.perform(get("/api/inventarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Producto X"));
    }

    @Test
    void testGetById_Found() throws Exception {
        Inventario i = new Inventario();
        i.setId(1L);
        i.setDescripcion("Producto X");
        i.setCantidad(10);
        i.setFechaRegistro(LocalDate.now());

        when(inventarioService.findById(1L)).thenReturn(i);

        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Producto X"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(inventarioService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        Inventario i = new Inventario();
        i.setId(1L);
        i.setDescripcion("Producto Y");
        i.setCantidad(5);
        i.setFechaRegistro(LocalDate.now());

        when(inventarioService.save(any(Inventario.class))).thenReturn(i);

        mockMvc.perform(post("/api/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(i)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Producto Y"));
    }

    @Test
    void testUpdate_Found() throws Exception {
        Inventario existente = new Inventario();
        existente.setId(1L);
        existente.setDescripcion("Antiguo");
        existente.setCantidad(1);
        existente.setFechaRegistro(LocalDate.now());

        Inventario actualizado = new Inventario();
        actualizado.setId(1L);
        actualizado.setDescripcion("Actualizado");
        actualizado.setCantidad(20);
        actualizado.setFechaRegistro(LocalDate.now());

        when(inventarioService.findById(1L)).thenReturn(existente);
        when(inventarioService.save(any(Inventario.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Actualizado"));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        when(inventarioService.findById(1L)).thenReturn(null);

        Inventario actualizado = new Inventario();
        actualizado.setDescripcion("Nuevo");
        actualizado.setCantidad(10);
        actualizado.setFechaRegistro(LocalDate.now());

        mockMvc.perform(put("/api/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {
        Inventario i = new Inventario();
        i.setId(1L);

        when(inventarioService.findById(1L)).thenReturn(i);

        mockMvc.perform(delete("/api/inventarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(inventarioService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/inventarios/1"))
                .andExpect(status().isNotFound());
    }
}
