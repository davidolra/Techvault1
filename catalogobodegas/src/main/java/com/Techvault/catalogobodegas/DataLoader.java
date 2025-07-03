package com.Techvault.catalogobodegas;

import com.Techvault.catalogobodegas.model.Bodega;
import com.Techvault.catalogobodegas.service.BodegaService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BodegaService bodegaService;
    private final Faker faker = new Faker();

    public DataLoader(BodegaService bodegaService) {
        this.bodegaService = bodegaService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bodegaService.findAll().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Bodega bodega = new Bodega();
                bodega.setNombre("Bodega " + faker.company().name());
                bodega.setUbicacion(faker.address().city());
                bodega.setTamaño(faker.number().randomDouble(2, 50, 500));  // entre 50 y 500 m2
                bodega.setPrecio(faker.number().randomDouble(2, 100000, 500000)); // precio entre 100k y 500k
                bodega.setEstado(faker.options().option("disponible", "ocupada", "mantenimiento"));
                bodega.setDescripcion(faker.lorem().sentence(5));
                bodegaService.save(bodega);
            }
            System.out.println("Datos de bodegas generados automáticamente.");
        }
    }
}
