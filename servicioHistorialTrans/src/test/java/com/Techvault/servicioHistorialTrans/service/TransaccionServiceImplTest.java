package com.Techvault.servicioHistorialTrans.service;

import com.Techvault.servicioHistorialTrans.model.Transaccion;
import com.Techvault.servicioHistorialTrans.repository.TransaccionRepository;
import com.Techvault.servicioHistorialTrans.service.impl.TransaccionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionServiceImplTest {

    @Mock
    private TransaccionRepository repository;

    @InjectMocks
    private TransaccionServiceImpl service;

    private Transaccion transaccion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transaccion = new Transaccion();
        transaccion.setId(1L);
        transaccion.setTipo("Pago");
        transaccion.setDescripcion("Desc");
        transaccion.setMonto(1500.0);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setIdReferencia(100L);
        transaccion.setServicioOrigen("Pago");
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(transaccion));
        List<Transaccion> result = service.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(transaccion));
        Transaccion result = service.findById(1L);
        assertNotNull(result);
        assertEquals("Pago", result.getTipo());
        verify(repository).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        Transaccion result = service.findById(2L);
        assertNull(result);
        verify(repository).findById(2L);
    }

    @Test
    void testSave() {
        when(repository.save(transaccion)).thenReturn(transaccion);
        Transaccion saved = service.save(transaccion);
        assertNotNull(saved);
        assertEquals(transaccion.getTipo(), saved.getTipo());
        verify(repository).save(transaccion);
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository).deleteById(1L);
    }
}
