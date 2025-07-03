package com.Techvault.catalogobodegas;

import com.Techvault.catalogobodegas.model.Bodega;
import com.Techvault.catalogobodegas.repository.BodegaRepository;
import com.Techvault.catalogobodegas.service.impl.BodegaServiceImpl;
import com.Techvault.catalogobodegas.service.BodegaService;

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
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private BodegaServiceImpl bodegaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    public void testFindAll() {
        when(bodegaRepository.findAll()).thenReturn(List.of(
                new Bodega(1L, "Bodega Central", "Santiago", 100.0, 200000.0, "disponible", "Espaciosa y bien ubicada")
        ));

        List<Bodega> bodegas = bodegaService.findAll();
        assertNotNull(bodegas);
        assertEquals(1, bodegas.size());
        assertEquals("Bodega Central", bodegas.get(0).getNombre());
    }

    @Test
    public void testFindById() {
        Bodega bodega = new Bodega(2L, "Bodega Norte", "Quilicura", 150.0, 300000.0, "ocupada", "Ideal para logística");
        when(bodegaRepository.findById(2L)).thenReturn(Optional.of(bodega));

        Bodega resultado = bodegaService.findById(2L);
        assertNotNull(resultado);
        assertEquals("Bodega Norte", resultado.getNombre());
    }

    @Test
    public void testSave() {
        Bodega bodega = new Bodega(null, "Bodega Sur", "Maipú", 120.0, 250000.0, "disponible", "Moderna y amplia");
        Bodega guardada = new Bodega(3L, "Bodega Sur", "Maipú", 120.0, 250000.0, "disponible", "Moderna y amplia");

        when(bodegaRepository.save(bodega)).thenReturn(guardada);

        Bodega resultado = bodegaService.save(bodega);
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Bodega Sur", resultado.getNombre());
    }


    @Test
    public void testDeleteById() {
        Long id = 4L;
        doNothing().when(bodegaRepository).deleteById(id);

        bodegaService.deleteById(id);

        verify(bodegaRepository, times(1)).deleteById(id);
    }
}
