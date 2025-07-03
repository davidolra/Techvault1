package com.Techvault.servicioInventario;

import com.Techvault.servicioInventario.model.Bodega;
import com.Techvault.servicioInventario.model.Cliente;
import com.Techvault.servicioInventario.model.Inventario;
import com.Techvault.servicioInventario.repository.BodegaRepository;
import com.Techvault.servicioInventario.repository.ClienteRepository;
import com.Techvault.servicioInventario.repository.InventarioRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

@Component
public class DataLoaderservicioInventario implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final BodegaRepository bodegaRepository;
    private final InventarioRepository inventarioRepository;
    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    public DataLoaderservicioInventario(ClienteRepository clienteRepository,
                                BodegaRepository bodegaRepository,
                                InventarioRepository inventarioRepository) {
        this.clienteRepository = clienteRepository;
        this.bodegaRepository = bodegaRepository;
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public void run(String... args) {
        // Limpiar las tablas
        inventarioRepository.deleteAll();
        bodegaRepository.deleteAll();
        clienteRepository.deleteAll();

        // Crear 5 clientes
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setId((long) (i + 1)); // Asignación manual de ID si no es autogenerado
            cliente.setNombre(faker.name().fullName());
            cliente.setEmail(faker.internet().emailAddress());
            clienteRepository.save(cliente);
        }

        // Crear 5 bodegas
        for (int i = 0; i < 5; i++) {
            Bodega bodega = new Bodega();
            bodega.setId((long) (i + 1)); // Asignación manual de ID si no es autogenerado
            bodega.setDireccion(faker.address().streetAddress());
            bodega.setCiudad(faker.address().city());
            bodegaRepository.save(bodega);
        }

        // Crear 10 inventarios
        for (int i = 0; i < 10; i++) {
            Inventario inventario = new Inventario();
            inventario.setDescripcion(faker.commerce().productName());
            inventario.setCantidad(random.nextInt(50) + 1);
            inventario.setFechaRegistro(LocalDate.now().minusDays(random.nextInt(30)));
            inventario.setCliente(clienteRepository.findById((long) ((i % 5) + 1)).orElse(null));
            inventario.setBodega(bodegaRepository.findById((long) ((i % 5) + 1)).orElse(null));
            inventarioRepository.save(inventario);
        }

        System.out.println("✔ Datos de prueba para servicioInventario cargados correctamente.");
    }
}
