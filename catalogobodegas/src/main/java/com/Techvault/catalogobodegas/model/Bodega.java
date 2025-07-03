package com.Techvault.catalogobodegas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String ubicacion;

    private Double tamaño; // en metros cuadrados, por ejemplo

    private Double precio; // precio de arriendo mensual

    private String estado; // disponible, ocupada, mantenimiento, etc.

    private String descripcion; // características adicionales

    // Constructores

    public Bodega() {}

    public Bodega(Long id, String nombre, String ubicacion, Double tamaño, Double precio, String estado, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tamaño = tamaño;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Double getTamaño() { return tamaño; }
    public void setTamaño(Double tamaño) { this.tamaño = tamaño; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}











