package com.Techvault.servicioContratacion.controller;

import com.Techvault.servicioContratacion.model.Cliente;
import com.Techvault.servicioContratacion.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void testGetAllClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Pedro");
        cliente.setRut("12345678-9");
        cliente.setEmail("pedro@mail.com");

        when(clienteService.findAll()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Pedro"))
                .andExpect(jsonPath("$[0].rut").value("12345678-9"))
                .andExpect(jsonPath("$[0].email").value("pedro@mail.com"));
    }
}
