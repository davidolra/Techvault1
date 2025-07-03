package com.Techvault.servicioPago.controller;

import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.model.Pago;
import com.Techvault.servicioPago.service.FacturaService;
import com.Techvault.servicioPago.service.PagoService;
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

@WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setMonto(500.0);
        pago.setFechaPago(LocalDateTime.now());

        Factura factura = new Factura();
        factura.setId(1L);
        pago.setFactura(factura);

        List<Pago> lista = Collections.singletonList(pago);

        when(pagoService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto").value(500.0));
    }

    @Test
    void testGetById_Found() throws Exception {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setMonto(700.0);
        pago.setFechaPago(LocalDateTime.now());

        Factura factura = new Factura();
        factura.setId(1L);
        pago.setFactura(factura);

        when(pagoService.findById(1L)).thenReturn(pago);

        mockMvc.perform(get("/api/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(700.0));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(pagoService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/pagos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate_Success() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);

        Pago pago = new Pago();
        pago.setMonto(1000);
        pago.setFechaPago(LocalDateTime.now());
        pago.setFactura(factura);

        Pago pagoGuardado = new Pago();
        pagoGuardado.setId(1L);
        pagoGuardado.setMonto(pago.getMonto());
        pagoGuardado.setFechaPago(pago.getFechaPago());
        pagoGuardado.setFactura(factura);

        when(facturaService.findById(1L)).thenReturn(factura);
        when(pagoService.save(any(Pago.class))).thenReturn(pagoGuardado);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreate_BadRequest_NoFactura() throws Exception {
        Pago pago = new Pago(); // factura null

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreate_BadRequest_FacturaNoExiste() throws Exception {
        Factura factura = new Factura();
        factura.setId(99L);

        Pago pago = new Pago();
        pago.setFactura(factura);

        when(facturaService.findById(99L)).thenReturn(null);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_Success() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);

        Pago pagoExistente = new Pago();
        pagoExistente.setId(1L);
        pagoExistente.setFactura(factura);
        pagoExistente.setMonto(500);

        Pago pagoNuevo = new Pago();
        pagoNuevo.setMonto(1500);
        pagoNuevo.setFechaPago(LocalDateTime.now());
        pagoNuevo.setFactura(factura);

        when(pagoService.findById(1L)).thenReturn(pagoExistente);
        when(facturaService.findById(1L)).thenReturn(factura);
        when(pagoService.save(any(Pago.class))).thenReturn(pagoExistente);

        mockMvc.perform(put("/api/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoNuevo)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        when(pagoService.findById(1L)).thenReturn(null);

        Pago pago = new Pago();
        pago.setFactura(new Factura());
        pago.getFactura().setId(1L);

        mockMvc.perform(put("/api/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdate_BadRequest_NoFactura() throws Exception {
        Pago pagoExistente = new Pago();
        pagoExistente.setId(1L);

        Pago pagoNuevo = new Pago(); // factura null

        when(pagoService.findById(1L)).thenReturn(pagoExistente);

        mockMvc.perform(put("/api/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoNuevo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_Found() throws Exception {
        Pago pago = new Pago();
        pago.setId(1L);

        when(pagoService.findById(1L)).thenReturn(pago);
        doNothing().when(pagoService).deleteById(1L);

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isNoContent());

        verify(pagoService, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(pagoService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isNotFound());
    }
}
