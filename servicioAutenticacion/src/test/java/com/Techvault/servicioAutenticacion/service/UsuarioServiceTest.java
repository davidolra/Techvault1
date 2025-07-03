package com.Techvault.servicioAutenticacion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Techvault.servicioAutenticacion.model.Usuario;
import com.Techvault.servicioAutenticacion.repository.UsuarioRepository;
import com.Techvault.servicioAutenticacion.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testRegistrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setEmail("juan@mail.com");
        usuario.setPassword("123456");
        usuario.setRol("CLIENTE");

        // Mock del repo: simula que guarda y retorna un usuario con ID 1
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        Usuario registrado = usuarioService.registrarUsuario(usuario);

        assertNotNull(registrado);
        assertEquals(1L, registrado.getId());
        assertEquals("Juan", registrado.getNombre());
        // Verificar que password esté encriptada (no igual a la original)
        assertNotEquals("123456", registrado.getPassword());
        assertTrue(passwordEncoder.matches("123456", registrado.getPassword()));
    }

    @Test
    public void testLogin_Success() {
        String email = "ana@mail.com";
        String rawPass = "password";
        String encodedPass = passwordEncoder.encode(rawPass);

        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNombre("Ana");
        usuario.setEmail(email);
        usuario.setPassword(encodedPass);
        usuario.setRol("ADMIN");

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Optional<Usuario> loginResult = usuarioService.login(email, rawPass);

        assertTrue(loginResult.isPresent());
        assertEquals("Ana", loginResult.get().getNombre());
    }

    @Test
    public void testLogin_Fail_WrongPassword() {
        String email = "ana@mail.com";
        String rawPass = "password";
        String wrongPass = "wrongpass";
        String encodedPass = passwordEncoder.encode(rawPass);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(encodedPass);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Optional<Usuario> loginResult = usuarioService.login(email, wrongPass);

        assertTrue(loginResult.isEmpty());
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario();
        usuario.setId(5L);
        usuario.setNombre("Carlos");
        when(usuarioRepository.findById(5L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findById(5L);
        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getNombre());
    }

    @Test
    public void testActualizarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setNombre("Luis");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario actualizado = usuarioService.actualizarUsuario(usuario);
        assertEquals("Luis", actualizado.getNombre());
    }

    @Test
    public void testEliminarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.findAll();
        assertEquals(1, usuarios.size());
        assertEquals("Test", usuarios.get(0).getNombre());
    }
}
