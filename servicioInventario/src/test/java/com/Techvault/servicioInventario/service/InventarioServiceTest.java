package com.Techvault.servicioInventario.service;

import com.Techvault.servicioInventario.model.Inventario;
import com.Techvault.servicioInventario.repository.InventarioRepository;
import com.Techvault.servicioInventario.service.impl.InventarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Inventario i1 = new Inventario();
        i1.setDescripcion("Producto A");
        List<Inventario> lista = Arrays.asList(i1);
        when(inventarioRepository.findAll()).thenReturn(lista);

        List<Inventario> result = inventarioService.findAll();
        assertEquals(1, result.size());
        assertEquals("Producto A", result.get(0).getDescripcion());
    }

    @Test
    void testFindById_Found() {
        Inventario i = new Inventario();
        i.setId(1L);
        i.setDescripcion("Producto B");

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(i));

        Inventario result = inventarioService.findById(1L);
        assertNotNull(result);
        assertEquals("Producto B", result.getDescripcion());
    }

    @Test
    void testFindById_NotFound() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());
        Inventario result = inventarioService.findById(1L);
        assertNull(result);
    }

    @Test
    void testSave() {
        Inventario i = new Inventario();
        i.setDescripcion("Producto C");
        when(inventarioRepository.save(i)).thenReturn(i);
        Inventario result = inventarioService.save(i);
        assertEquals("Producto C", result.getDescripcion());
    }

    @Test
    void testDeleteById() {
        inventarioService.deleteById(1L);
        verify(inventarioRepository, times(1)).deleteById(1L);
    }
}
