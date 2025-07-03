package com.Techvault.servicioAutenticacion.controller;

import com.Techvault.servicioAutenticacion.model.Usuario;
import com.Techvault.servicioAutenticacion.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false) // ❗️Desactiva la seguridad para los tests
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Usuario usuario = new Usuario();

    @BeforeEach
    void setUp() {
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setEmail("test@mail.com");
        usuario.setPassword("encryptedpass");
        usuario.setRol("CLIENTE");
    }

    @Test
    void testRegistrarUsuario() throws Exception {
        when(usuarioService.registrarUsuario(any())).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuario.getId()))
                .andExpect(jsonPath("$.nombre").value(usuario.getNombre()));
    }

    @Test
    void testLogin_Success() throws Exception {
        UsuarioController.LoginRequest loginRequest = new UsuarioController.LoginRequest();
        loginRequest.email = "test@mail.com";
        loginRequest.password = "password";

        when(usuarioService.login(loginRequest.email, loginRequest.password))
                .thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(usuario.getEmail()));
    }

    @Test
    void testLogin_Fail() throws Exception {
        UsuarioController.LoginRequest loginRequest = new UsuarioController.LoginRequest();
        loginRequest.email = "wrong@mail.com";
        loginRequest.password = "badpass";

        when(usuarioService.login(anyString(), anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@mail.com"));
    }

    @Test
    void testGetUsuarioById_Found() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetUsuarioById_NotFound() throws Exception {
        when(usuarioService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarUsuario_Found() throws Exception {
        Usuario actualizado = new Usuario();
        actualizado.setId(1L);
        actualizado.setNombre("Nuevo Nombre");
        actualizado.setEmail("nuevo@mail.com");
        actualizado.setRol("ADMIN");

        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioService.actualizarUsuario(any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Nombre"));
    }

    @Test
    void testActualizarUsuario_NotFound() throws Exception {
        when(usuarioService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }

    @Autowired
    private MockMvc mockMvc;
}
