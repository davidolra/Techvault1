package com.Techvault.servicioContratacion.service;

import com.Techvault.servicioContratacion.model.Cliente;
import com.Techvault.servicioContratacion.repository.ClienteRepository;
import com.Techvault.servicioContratacion.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Cliente Test");
        cliente.setRut("12345678-9");
        cliente.setEmail("cliente@test.com");
    }

    @Test
    void testFindAll() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> clientes = clienteService.findAll();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void testFindById_Found() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Cliente result = clienteService.findById(1L);
        assertNotNull(result);
        assertEquals("Cliente Test", result.getNombre());
        verify(clienteRepository).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        Cliente result = clienteService.findById(2L);
        assertNull(result);
        verify(clienteRepository).findById(2L);
    }

    @Test
    void testSave() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente saved = clienteService.save(cliente);
        assertNotNull(saved);
        assertEquals(cliente.getNombre(), saved.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testDeleteById() {
        doNothing().when(clienteRepository).deleteById(1L);
        clienteService.deleteById(1L);
        verify(clienteRepository).deleteById(1L);
    }
}
