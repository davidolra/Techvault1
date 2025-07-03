package com.Techvault.servicioPago.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaEmision;

    private double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("facturas")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("factura")
    private List<Pago> pagos;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
