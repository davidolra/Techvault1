package com.Techvault.servicioPago;

import com.Techvault.servicioPago.model.Cliente;
import com.Techvault.servicioPago.model.Factura;
import com.Techvault.servicioPago.model.Pago;
import com.Techvault.servicioPago.repository.ClienteRepository;
import com.Techvault.servicioPago.repository.FacturaRepository;
import com.Techvault.servicioPago.repository.PagoRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

@Component
public class DataLoaderPago implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;

    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    public DataLoaderPago(ClienteRepository clienteRepository, FacturaRepository facturaRepository, PagoRepository pagoRepository) {
        this.clienteRepository = clienteRepository;
        this.facturaRepository = facturaRepository;
        this.pagoRepository = pagoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiar tablas (opcional, para evitar duplicados)
        pagoRepository.deleteAll();
        facturaRepository.deleteAll();
        clienteRepository.deleteAll();

        // Crear 5 clientes con facturas y pagos
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().fullName());
            cliente.setRut(faker.idNumber().valid());
            cliente.setEmail(faker.internet().emailAddress());

            cliente = clienteRepository.save(cliente);

            // Crear 1 a 3 facturas para cada cliente
            int numFacturas = random.nextInt(3) + 1;
            for (int j = 0; j < numFacturas; j++) {
                Factura factura = new Factura();
                factura.setCliente(cliente);
                factura.setFechaEmision(LocalDate.now().minusDays(random.nextInt(30)));
                factura.setTotal(faker.number().randomDouble(2, 1000, 100000));
                factura = facturaRepository.save(factura);

                // Crear 1 o 2 pagos para cada factura
                int numPagos = random.nextInt(2) + 1;
                for (int k = 0; k < numPagos; k++) {
                    Pago pago = new Pago();
                    pago.setFactura(factura);
                    pago.setFechaPago(LocalDateTime.now().minusDays(random.nextInt(30)));
                    pago.setMonto(faker.number().randomDouble(2, 500, Math.round(factura.getTotal())));

                    pagoRepository.save(pago);
                }
            }
        }

        System.out.println("Datos de prueba para ServicioPago cargados con Faker.");
    }
}
