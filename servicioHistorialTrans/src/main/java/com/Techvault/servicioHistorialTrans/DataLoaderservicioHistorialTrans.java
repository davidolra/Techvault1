package com.Techvault.servicioHistorialTrans;

import com.Techvault.servicioHistorialTrans.model.Transaccion;
import com.Techvault.servicioHistorialTrans.repository.TransaccionRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

@Component
public class DataLoaderservicioHistorialTrans implements CommandLineRunner {

    private final TransaccionRepository transaccionRepository;
    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    public DataLoaderservicioHistorialTrans(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiar tabla para evitar duplicados
        transaccionRepository.deleteAll();

        String[] tipos = {"Pago", "Reserva", "Contrato", "Cancelación"};
        String[] servicios = {"Pago", "Contratacion", "Factura", "Inventario"};

        for (int i = 0; i < 15; i++) {
            Transaccion t = new Transaccion();
            t.setTipo(faker.options().option(tipos));
            t.setDescripcion(faker.lorem().sentence());
            t.setMonto(faker.number().randomDouble(2, 1000, 100000));
            t.setFecha(LocalDateTime.now().minusDays(random.nextInt(30)).minusHours(random.nextInt(24)));
            t.setIdReferencia((long) (random.nextInt(10) + 1)); // IDs aleatorios para simular referencias
            t.setServicioOrigen(faker.options().option(servicios));

            transaccionRepository.save(t);
        }

        System.out.println("Datos faker cargados en historial de transacciones.");
    }
}
