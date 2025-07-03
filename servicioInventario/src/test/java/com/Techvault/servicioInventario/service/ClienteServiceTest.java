package com.Techvault.servicioInventario.service;

import com.Techvault.servicioInventario.model.Cliente;
import com.Techvault.servicioInventario.repository.ClienteRepository;
import com.Techvault.servicioInventario.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    private ClienteRepository clienteRepository;
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(new Cliente()));
        assertEquals(1, clienteService.findAll().size());
    }

    @Test
    void testFindById() {
        Cliente c = new Cliente();
        c.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));

        Cliente result = clienteService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSave() {
        Cliente c = new Cliente();
        when(clienteRepository.save(c)).thenReturn(c);
        assertEquals(c, clienteService.save(c));
    }

    @Test
    void testDelete() {
        doNothing().when(clienteRepository).deleteById(1L);
        clienteService.deleteById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
