package com.Techvault.servicioTransporte.controller;

import com.Techvault.servicioTransporte.model.Transporte;
import com.Techvault.servicioTransporte.service.TransporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransporteController.class)
public class TransporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransporteService transporteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Transporte t = new Transporte();
        t.setId(1L);
        t.setTipoTransporte("Entrega");
        t.setEstado("Pendiente");
        t.setFechaHoraProgramada(LocalDateTime.now());
        t.setDireccionOrigen("Origen 123");
        t.setDireccionDestino("Destino 456");
        t.setResponsable("Juan Perez");

        List<Transporte> lista = Collections.singletonList(t);

        when(transporteService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/transportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoTransporte").value("Entrega"))
                .andExpect(jsonPath("$[0].responsable").value("Juan Perez"));
    }

    @Test
    void testGetById_Found() throws Exception {
        Transporte t = new Transporte();
        t.setId(1L);
        t.setTipoTransporte("Recolecta");
        t.setEstado("En curso");
        t.setFechaHoraProgramada(LocalDateTime.now());
        t.setDireccionOrigen("Origen A");
        t.setDireccionDestino("Destino B");
        t.setResponsable("Maria Lopez");

        when(transporteService.findById(1L)).thenReturn(t);

        mockMvc.perform(get("/api/transportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoTransporte").value("Recolecta"))
                .andExpect(jsonPath("$.responsable").value("Maria Lopez"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(transporteService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/transportes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        Transporte t = new Transporte();
        t.setTipoTransporte("Entrega");
        t.setEstado("Pendiente");
        t.setFechaHoraProgramada(LocalDateTime.now());
        t.setDireccionOrigen("Origen");
        t.setDireccionDestino("Destino");
        t.setResponsable("Carlos");

        Transporte guardado = new Transporte();
        guardado.setId(1L);
        guardado.setTipoTransporte(t.getTipoTransporte());
        guardado.setEstado(t.getEstado());
        guardado.setFechaHoraProgramada(t.getFechaHoraProgramada());
        guardado.setDireccionOrigen(t.getDireccionOrigen());
        guardado.setDireccionDestino(t.getDireccionDestino());
        guardado.setResponsable(t.getResponsable());

        when(transporteService.save(any(Transporte.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/transportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipoTransporte").value("Entrega"));
    }

    @Test
    void testUpdate_Found() throws Exception {
        Transporte existente = new Transporte();
        existente.setId(1L);
        existente.setTipoTransporte("Entrega");
        existente.setEstado("Pendiente");
        existente.setFechaHoraProgramada(LocalDateTime.now().minusDays(1));
        existente.setDireccionOrigen("Origen viejo");
        existente.setDireccionDestino("Destino viejo");
        existente.setResponsable("Responsable viejo");

        Transporte actualizado = new Transporte();
        actualizado.setTipoTransporte("Recolecta");
        actualizado.setEstado("En curso");
        actualizado.setFechaHoraProgramada(LocalDateTime.now());
        actualizado.setDireccionOrigen("Origen nuevo");
        actualizado.setDireccionDestino("Destino nuevo");
        actualizado.setResponsable("Responsable nuevo");

        Transporte guardado = new Transporte();
        guardado.setId(1L);
        guardado.setTipoTransporte(actualizado.getTipoTransporte());
        guardado.setEstado(actualizado.getEstado());
        guardado.setFechaHoraProgramada(actualizado.getFechaHoraProgramada());
        guardado.setDireccionOrigen(actualizado.getDireccionOrigen());
        guardado.setDireccionDestino(actualizado.getDireccionDestino());
        guardado.setResponsable(actualizado.getResponsable());

        when(transporteService.findById(1L)).thenReturn(existente);
        when(transporteService.save(any(Transporte.class))).thenReturn(guardado);

        mockMvc.perform(put("/api/transportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoTransporte").value("Recolecta"))
                .andExpect(jsonPath("$.estado").value("En curso"))
                .andExpect(jsonPath("$.responsable").value("Responsable nuevo"));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        Transporte t = new Transporte();
        t.setTipoTransporte("Entrega");

        when(transporteService.findById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/transportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_Found() throws Exception {
        Transporte t = new Transporte();
        t.setId(1L);

        when(transporteService.findById(1L)).thenReturn(t);
        doNothing().when(transporteService).deleteById(1L);

        mockMvc.perform(delete("/api/transportes/1"))
                .andExpect(status().isNoContent());

        verify(transporteService, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(transporteService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/transportes/1"))
                .andExpect(status().isNotFound());
    }
}
