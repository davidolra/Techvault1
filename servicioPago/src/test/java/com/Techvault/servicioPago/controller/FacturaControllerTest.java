package com.Techvault.servicioPago.controller;

import com.Techvault.servicioPago.model.Cliente;
import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.service.ClienteService;
import com.Techvault.servicioPago.service.FacturaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setFechaEmision(LocalDate.now());
        factura.setTotal(1500.0);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Cliente X");
        factura.setCliente(cliente);

        List<Factura> lista = Collections.singletonList(factura);

        when(facturaService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].total").value(1500.0));
    }

    @Test
    void testGetById_Found() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setFechaEmision(LocalDate.now());
        factura.setTotal(1500.0);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Cliente X");
        factura.setCliente(cliente);

        when(facturaService.findById(1L)).thenReturn(factura);

        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1500.0));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(facturaService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate_Success() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Factura factura = new Factura();
        factura.setFechaEmision(LocalDate.now());
        factura.setTotal(2000);
        factura.setCliente(cliente);

        Factura facturaGuardada = new Factura();
        facturaGuardada.setId(1L);
        facturaGuardada.setFechaEmision(factura.getFechaEmision());
        facturaGuardada.setTotal(factura.getTotal());
        facturaGuardada.setCliente(cliente);

        when(clienteService.findById(1L)).thenReturn(cliente);
        when(facturaService.save(any(Factura.class))).thenReturn(facturaGuardada);

        mockMvc.perform(post("/api/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreate_BadRequest_NoCliente() throws Exception {
        Factura factura = new Factura(); // cliente null

        mockMvc.perform(post("/api/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreate_BadRequest_ClienteNoExiste() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(99L);

        Factura factura = new Factura();
        factura.setCliente(cliente);

        when(clienteService.findById(99L)).thenReturn(null);

        mockMvc.perform(post("/api/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_Success() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Factura facturaExistente = new Factura();
        facturaExistente.setId(1L);
        facturaExistente.setCliente(cliente);
        facturaExistente.setTotal(1000);

        Factura facturaNueva = new Factura();
        facturaNueva.setFechaEmision(LocalDate.now());
        facturaNueva.setTotal(3000);
        facturaNueva.setCliente(cliente);

        when(facturaService.findById(1L)).thenReturn(facturaExistente);
        when(clienteService.findById(1L)).thenReturn(cliente);
        when(facturaService.save(any(Factura.class))).thenReturn(facturaExistente);

        mockMvc.perform(put("/api/facturas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facturaNueva)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        when(facturaService.findById(1L)).thenReturn(null);

        Factura factura = new Factura();
        factura.setCliente(new Cliente());
        factura.getCliente().setId(1L);

        mockMvc.perform(put("/api/facturas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdate_BadRequest_NoCliente() throws Exception {
        Factura facturaExistente = new Factura();
        facturaExistente.setId(1L);

        Factura facturaNueva = new Factura(); // cliente null

        when(facturaService.findById(1L)).thenReturn(facturaExistente);

        mockMvc.perform(put("/api/facturas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facturaNueva)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_Found() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);

        when(facturaService.findById(1L)).thenReturn(factura);
        doNothing().when(facturaService).deleteById(1L);

        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isNoContent());

        verify(facturaService, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(facturaService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isNotFound());
    }
}
