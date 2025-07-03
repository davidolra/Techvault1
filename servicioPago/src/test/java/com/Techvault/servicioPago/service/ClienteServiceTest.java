package com.Techvault.servicioPago.service;

import com.Techvault.servicioPago.model.Cliente;
import com.Techvault.servicioPago.repository.ClienteRepository;
import com.Techvault.servicioPago.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;  // Asumiendo que tienes esta implementación

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Cliente c1 = new Cliente();
        c1.setNombre("Cliente 1");
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(c1));

        List<Cliente> result = clienteService.findAll();
        assertEquals(1, result.size());
        assertEquals("Cliente 1", result.get(0).getNombre());
    }

    @Test
    void testFindById_Found() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNombre("Cliente 1");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));

        Cliente result = clienteService.findById(1L);
        assertNotNull(result);
        assertEquals("Cliente 1", result.getNombre());
    }

    @Test
    void testFindById_NotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Cliente result = clienteService.findById(1L);
        assertNull(result);
    }

    @Test
    void testSave() {
        Cliente c = new Cliente();
        c.setNombre("Cliente Guardado");

        when(clienteRepository.save(c)).thenReturn(c);

        Cliente result = clienteService.save(c);
        assertEquals("Cliente Guardado", result.getNombre());
    }

    @Test
    void testDeleteById() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deleteById(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
