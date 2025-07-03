package com.Techvault.catalogobodegas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.Techvault.catalogobodegas.model.Bodega;
import com.Techvault.catalogobodegas.repository.BodegaRepository;
import com.Techvault.catalogobodegas.service.impl.BodegaServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BodegaServiceTest {

    @Autowired
    private BodegaService bodegaService;

    @MockBean
    private BodegaRepository bodegaRepository;

    @Test
    public void testFindAll() {
        Bodega bodega = new Bodega(1L, "Bodega Central", "Santiago", 120.0, 250000.0, "disponible", "Bodega amplia");
        when(bodegaRepository.findAll()).thenReturn(List.of(bodega));

        List<Bodega> bodegas = bodegaService.findAll();

        assertNotNull(bodegas);
        assertEquals(1, bodegas.size());
        assertEquals("Bodega Central", bodegas.get(0).getNombre());
    }

    @Test
    public void testFindById() {
        Bodega bodega = new Bodega(1L, "Bodega Norte", "Valparaiso", 150.0, 300000.0, "ocupada", "Bodega cerca del puerto");
        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));

        Bodega resultado = bodegaService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Bodega Norte", resultado.getNombre());
    }

    @Test
    public void testFindById_NotFound() {
        when(bodegaRepository.findById(99L)).thenReturn(Optional.empty());

        Bodega resultado = bodegaService.findById(99L);

        assertNull(resultado);
    }

    @Test
    public void testSave() {
        Bodega bodega = new Bodega(null, "Bodega Sur", "Concepcion", 200.0, 350000.0, "disponible", "Bodega accesible");
        Bodega bodegaGuardada = new Bodega(2L, "Bodega Sur", "Concepcion", 200.0, 350000.0, "disponible", "Bodega accesible");

        when(bodegaRepository.save(bodega)).thenReturn(bodegaGuardada);

        Bodega resultado = bodegaService.save(bodega);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Bodega Sur", resultado.getNombre());
    }

    @Test
    public void testDeleteById() {
        Long id = 3L;
        doNothing().when(bodegaRepository).deleteById(id);

        bodegaService.deleteById(id);

        verify(bodegaRepository, times(1)).deleteById(id);
    }
}
