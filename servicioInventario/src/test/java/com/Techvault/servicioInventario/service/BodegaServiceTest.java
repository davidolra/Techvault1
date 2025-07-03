package com.Techvault.servicioInventario.service;

import com.Techvault.servicioInventario.model.Bodega;
import com.Techvault.servicioInventario.repository.BodegaRepository;
import com.Techvault.servicioInventario.service.impl.BodegaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BodegaServiceTest {

    @Mock
    private BodegaRepository repository;

    @InjectMocks
    private BodegaServiceImpl service;

    private Bodega bodega;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bodega = new Bodega();
        bodega.setId(1L);
        bodega.setCiudad("Santiago");
        bodega.setDireccion("Calle falsa 123");
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(bodega));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(bodega));
        assertEquals("Santiago", service.findById(1L).getCiudad());
    }

    @Test
    void testSave() {
        when(repository.save(any())).thenReturn(bodega);
        assertEquals("Santiago", service.save(bodega).getCiudad());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}