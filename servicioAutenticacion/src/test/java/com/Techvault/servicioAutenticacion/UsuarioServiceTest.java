package com.Techvault.servicioAutenticacion;

import com.Techvault.servicioAutenticacion.model.Usuario;
import com.Techvault.servicioAutenticacion.repository.UsuarioRepository;
import com.Techvault.servicioAutenticacion.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("David");
        usuario.setEmail("david@mail.com");
        usuario.setPassword("1234");
        usuario.setRol("CLIENTE");

        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Usuario guardado = usuarioService.registrarUsuario(usuario);
        assertNotNull(guardado.getPassword());
        assertTrue(encoder.matches("1234", guardado.getPassword()));
    }

    @Test
    public void testLoginExitoso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword(encoder.encode("secreta"));

        when(usuarioRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.login("test@mail.com", "secreta");

        assertTrue(resultado.isPresent());
    }

    @Test
    public void testLoginFallido_porPasswordIncorrecta() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@mail.com");
        usuario.setPassword(encoder.encode("otraClave"));

        when(usuarioRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.login("test@mail.com", "secreta");

        assertFalse(resultado.isPresent());
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Laura");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findById(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Laura", resultado.get().getNombre());
    }

    @Test
    public void testActualizarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNombre("Genesis");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario actualizado = usuarioService.actualizarUsuario(usuario);
        assertEquals("Genesis", actualizado.getNombre());
    }

    @Test
    public void testEliminarUsuario() {
        Long id = 5L;

        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));
        List<Usuario> lista = usuarioService.findAll();
        assertEquals(2, lista.size());
    }
}
