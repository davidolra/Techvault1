package com.Techvault.servicioPago.service;

import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.repository.FacturaRepository;
import com.Techvault.servicioPago.service.impl.FacturaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Factura f1 = new Factura();
        f1.setTotal(1000);
        when(facturaRepository.findAll()).thenReturn(Arrays.asList(f1));

        List<Factura> result = facturaService.findAll();
        assertEquals(1, result.size());
        assertEquals(1000, result.get(0).getTotal());
    }

    @Test
    void testFindById_Found() {
        Factura f = new Factura();
        f.setId(1L);
        f.setTotal(2000);

        when(facturaRepository.findById(1L)).thenReturn(Optional.of(f));

        Factura result = facturaService.findById(1L);
        assertNotNull(result);
        assertEquals(2000, result.getTotal());
    }

    @Test
    void testFindById_NotFound() {
        when(facturaRepository.findById(1L)).thenReturn(Optional.empty());

        Factura result = facturaService.findById(1L);
        assertNull(result);
    }

    @Test
    void testSave() {
        Factura f = new Factura();
        f.setTotal(3000);

        when(facturaRepository.save(f)).thenReturn(f);

        Factura result = facturaService.save(f);
        assertEquals(3000, result.getTotal());
    }

    @Test
    void testDeleteById() {
        doNothing().when(facturaRepository).deleteById(1L);

        facturaService.deleteById(1L);

        verify(facturaRepository, times(1)).deleteById(1L);
    }
}
