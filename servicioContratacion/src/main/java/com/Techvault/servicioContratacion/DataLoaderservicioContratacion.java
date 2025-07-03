package com.Techvault.servicioContratacion;

import com.Techvault.servicioContratacion.model.Bodega;
import com.Techvault.servicioContratacion.model.Cliente;
import com.Techvault.servicioContratacion.model.Contrato;
import com.Techvault.servicioContratacion.repository.BodegaRepository;
import com.Techvault.servicioContratacion.repository.ClienteRepository;
import com.Techvault.servicioContratacion.repository.ContratoRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

@Component
public class DataLoaderservicioContratacion implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final BodegaRepository bodegaRepository;
    private final ContratoRepository contratoRepository;

    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    public DataLoaderservicioContratacion(ClienteRepository clienteRepository,
                                  BodegaRepository bodegaRepository,
                                  ContratoRepository contratoRepository) {
        this.clienteRepository = clienteRepository;
        this.bodegaRepository = bodegaRepository;
        this.contratoRepository = contratoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiar tablas para evitar duplicados
        contratoRepository.deleteAll();
        clienteRepository.deleteAll();
        bodegaRepository.deleteAll();

        // Crear 5 clientes
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().fullName());
            cliente.setRut(faker.idNumber().valid());
            cliente.setEmail(faker.internet().emailAddress());
            clienteRepository.save(cliente);
        }

        // Crear 5 bodegas
        for (int i = 0; i < 5; i++) {
            Bodega bodega = new Bodega();
            bodega.setUbicacion(faker.address().fullAddress());
            bodega.setTamaño(faker.number().randomDouble(2, 10, 100)); // tamaño entre 10 y 100
            bodega.setPrecio(faker.number().randomDouble(2, 100000, 500000));
            bodegaRepository.save(bodega);
        }

        // Crear 10 contratos
        for (int i = 0; i < 10; i++) {
            Contrato contrato = new Contrato();

            Cliente cliente = clienteRepository.findAll()
                    .get(random.nextInt(clienteRepository.findAll().size()));
            Bodega bodega = bodegaRepository.findAll()
                    .get(random.nextInt(bodegaRepository.findAll().size()));

            contrato.setCliente(cliente);
            contrato.setBodega(bodega);

            LocalDate fechaInicio = LocalDate.now().minusDays(random.nextInt(30));
            LocalDate fechaFin = fechaInicio.plusMonths(random.nextInt(12) + 1);

            contrato.setFechaInicio(fechaInicio);
            contrato.setFechaFin(fechaFin);

            double precio = bodega.getPrecio();
            int meses = (int) (fechaFin.toEpochDay() - fechaInicio.toEpochDay()) / 30;
            contrato.setMontoTotal(precio * meses);

            contrato.setEstado(faker.options().option("Activo", "Finalizado", "Cancelado"));

            contratoRepository.save(contrato);
        }

        System.out.println("✔ Datos de prueba para servicioContratacion cargados correctamente.");
    }
}
