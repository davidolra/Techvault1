package com.Techvault.servicioPago.service;

import com.Techvault.servicioPago.model.Pago;
import com.Techvault.servicioPago.repository.PagoRepository;
import com.Techvault.servicioPago.service.impl.PagoServiceImpl;
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

class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Pago p1 = new Pago();
        p1.setMonto(1000);
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(p1));

        List<Pago> result = pagoService.findAll();
        assertEquals(1, result.size());
        assertEquals(1000, result.get(0).getMonto());
    }

    @Test
    void testFindById_Found() {
        Pago p = new Pago();
        p.setId(1L);
        p.setMonto(2000);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(p));

        Pago result = pagoService.findById(1L);
        assertNotNull(result);
        assertEquals(2000, result.getMonto());
    }

    @Test
    void testFindById_NotFound() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());

        Pago result = pagoService.findById(1L);
        assertNull(result);
    }

    @Test
    void testSave() {
        Pago p = new Pago();
        p.setMonto(3000);

        when(pagoRepository.save(p)).thenReturn(p);

        Pago result = pagoService.save(p);
        assertEquals(3000, result.getMonto());
    }

    @Test
    void testDeleteById() {
        doNothing().when(pagoRepository).deleteById(1L);

        pagoService.deleteById(1L);

        verify(pagoRepository, times(1)).deleteById(1L);
    }
}
