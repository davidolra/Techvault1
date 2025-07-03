package com.Techvault.servicioAutenticacion.service;

import com.Techvault.servicioAutenticacion.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> login(String email, String password);
    Optional<Usuario> findById(Long id);
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    void eliminarTodos();
    List<Usuario> findAll();
}
