package com.Techvault.servicioTransporte;

import com.Techvault.servicioTransporte.model.Transporte;
import com.Techvault.servicioTransporte.repository.TransporteRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

@Component
public class DataLoaderservicioTransporte implements CommandLineRunner {

    private final TransporteRepository transporteRepository;
    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    public DataLoaderservicioTransporte(TransporteRepository transporteRepository) {
        this.transporteRepository = transporteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        transporteRepository.deleteAll();

        String[] tipos = {"Recolecta", "Entrega"};
        String[] estados = {"Pendiente", "En curso", "Finalizado"};

        for (int i = 0; i < 10; i++) {
            Transporte t = new Transporte();
            t.setTipoTransporte(tipos[random.nextInt(tipos.length)]);
            t.setEstado(estados[random.nextInt(estados.length)]);
            // Fecha programada aleatoria en próximos 10 días
            LocalDateTime fecha = LocalDateTime.now()
                    .plusDays(random.nextInt(10))
                    .withHour(random.nextInt(24))
                    .withMinute(random.nextInt(60));
            t.setFechaHoraProgramada(fecha);
            t.setDireccionOrigen(faker.address().streetAddress());
            t.setDireccionDestino(faker.address().streetAddress());
            t.setResponsable(faker.name().fullName());

            transporteRepository.save(t);
        }

        System.out.println("✔ Datos de prueba para servicioTransporte cargados.");
    }
}
