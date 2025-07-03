package com.Techvault.servicioTransporte.service;

import com.Techvault.servicioTransporte.model.Transporte;
import com.Techvault.servicioTransporte.repository.TransporteRepository;
import com.Techvault.servicioTransporte.service.impl.TransporteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransporteServiceImplTest {

    @Mock
    private TransporteRepository transporteRepository;

    @InjectMocks
    private TransporteServiceImpl transporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Transporte t1 = new Transporte();
        t1.setTipoTransporte("Entrega");
        when(transporteRepository.findAll()).thenReturn(Arrays.asList(t1));

        List<Transporte> result = transporteService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Entrega", result.get(0).getTipoTransporte());
    }

    @Test
    void testFindById_Found() {
        Transporte t = new Transporte();
        t.setId(1L);
        t.setTipoTransporte("Recolecta");

        when(transporteRepository.findById(1L)).thenReturn(Optional.of(t));

        Transporte result = transporteService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Recolecta", result.getTipoTransporte());
    }

    @Test
    void testFindById_NotFound() {
        when(transporteRepository.findById(1L)).thenReturn(Optional.empty());

        Transporte result = transporteService.findById(1L);

        assertNull(result);
    }

    @Test
    void testSave() {
        Transporte t = new Transporte();
        t.setTipoTransporte("Entrega");

        when(transporteRepository.save(t)).thenReturn(t);

        Transporte result = transporteService.save(t);

        assertNotNull(result);
        assertEquals("Entrega", result.getTipoTransporte());
    }

    @Test
    void testDeleteById() {
        doNothing().when(transporteRepository).deleteById(1L);

        transporteService.deleteById(1L);

        verify(transporteRepository, times(1)).deleteById(1L);
    }
}
