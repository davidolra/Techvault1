package com.Techvault.servicioInventario.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.Techvault.servicioInventario.model.Inventario;


import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    private Long id;

    private String nombre;

    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties("cliente")
    private List<Inventario> inventarios;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

}
