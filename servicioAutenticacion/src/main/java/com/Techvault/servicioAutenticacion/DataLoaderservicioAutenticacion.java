package com.Techvault.servicioAutenticacion;

import com.Techvault.servicioAutenticacion.model.Usuario;
import com.Techvault.servicioAutenticacion.service.UsuarioService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class DataLoaderservicioAutenticacion implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    public DataLoaderservicioAutenticacion(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiar usuarios existentes
        usuarioService.eliminarTodos(); // O si no tienes este método, elimina directamente desde repo antes de crear

        // Crear 5 usuarios de prueba
        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword("password123"); // contraseña base
            usuario.setRol(random.nextBoolean() ? "ADMIN" : "CLIENTE");

            // Usar el servicio para registrar y encriptar contraseña
            usuarioService.registrarUsuario(usuario);
        }

        System.out.println("✔ Datos de prueba para servicioAutenticacion cargados con contraseñas encriptadas.");
    }
}
