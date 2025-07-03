package com.Techvault.servicioContratacion.service;

import com.Techvault.servicioContratacion.model.Contrato;
import com.Techvault.servicioContratacion.repository.ContratoRepository;
import com.Techvault.servicioContratacion.service.impl.ContratoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratoServiceImplTest {

    @Mock
    private ContratoRepository contratoRepository;

    @InjectMocks
    private ContratoServiceImpl contratoService;

    private Contrato contrato;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contrato = new Contrato();
        contrato.setId(1L);
        contrato.setFechaInicio(LocalDate.now());
        contrato.setFechaFin(LocalDate.now().plusMonths(6));
        contrato.setMontoTotal(1200000.0);
        contrato.setEstado("Activo");
    }

    @Test
    void testFindAll() {
        when(contratoRepository.findAll()).thenReturn(List.of(contrato));
        List<Contrato> contratos = contratoService.findAll();
        assertNotNull(contratos);
        assertEquals(1, contratos.size());
        verify(contratoRepository).findAll();
    }

    @Test
    void testFindById_Found() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        Contrato result = contratoService.findById(1L);
        assertNotNull(result);
        assertEquals("Activo", result.getEstado());
        verify(contratoRepository).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(contratoRepository.findById(2L)).thenReturn(Optional.empty());
        Contrato result = contratoService.findById(2L);
        assertNull(result);
        verify(contratoRepository).findById(2L);
    }

    @Test
    void testSave() {
        when(contratoRepository.save(contrato)).thenReturn(contrato);
        Contrato saved = contratoService.save(contrato);
        assertNotNull(saved);
        assertEquals(contrato.getEstado(), saved.getEstado());
        verify(contratoRepository).save(contrato);
    }

    @Test
    void testDeleteById() {
        doNothing().when(contratoRepository).deleteById(1L);
        contratoService.deleteById(1L);
        verify(contratoRepository).deleteById(1L);
    }
}
