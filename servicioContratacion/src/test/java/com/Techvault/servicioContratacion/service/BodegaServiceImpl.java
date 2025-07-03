package com.Techvault.servicioContratacion.service;

import com.Techvault.servicioContratacion.model.Bodega;
import com.Techvault.servicioContratacion.repository.BodegaRepository;
import com.Techvault.servicioContratacion.service.impl.BodegaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BodegaServiceImplTest {

    @Mock
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private BodegaServiceImpl bodegaService;

    private Bodega bodega;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bodega = new Bodega();
        bodega.setId(1L);
        bodega.setUbicacion("Ubicación Test");
        bodega.setTamaño(50.0);
        bodega.setPrecio(150000.0);
    }

    @Test
    void testFindAll() {
        when(bodegaRepository.findAll()).thenReturn(List.of(bodega));
        List<Bodega> bodegas = bodegaService.findAll();
        assertNotNull(bodegas);
        assertEquals(1, bodegas.size());
        verify(bodegaRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Found() {
        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
        Bodega result = bodegaService.findById(1L);
        assertNotNull(result);
        assertEquals("Ubicación Test", result.getUbicacion());
        verify(bodegaRepository).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(bodegaRepository.findById(2L)).thenReturn(Optional.empty());
        Bodega result = bodegaService.findById(2L);
        assertNull(result);
        verify(bodegaRepository).findById(2L);
    }

    @Test
    void testSave() {
        when(bodegaRepository.save(bodega)).thenReturn(bodega);
        Bodega saved = bodegaService.save(bodega);
        assertNotNull(saved);
        assertEquals(bodega.getUbicacion(), saved.getUbicacion());
        verify(bodegaRepository).save(bodega);
    }

    @Test
    void testDeleteById() {
        doNothing().when(bodegaRepository).deleteById(1L);
        bodegaService.deleteById(1L);
        verify(bodegaRepository).deleteById(1L);
    }
}
