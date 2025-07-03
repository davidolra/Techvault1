package com.Techvault.servicioTransporte.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoTransporte; // Ejemplo: "Recolecta", "Entrega"
    private String estado; // Ejemplo: "Pendiente", "En curso", "Finalizado"
    private LocalDateTime fechaHoraProgramada;
    private String direccionOrigen;
    private String direccionDestino;
    private String responsable;

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(String tipoTransporte) { this.tipoTransporte = tipoTransporte; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaHoraProgramada() { return fechaHoraProgramada; }
    public void setFechaHoraProgramada(LocalDateTime fechaHoraProgramada) { this.fechaHoraProgramada = fechaHoraProgramada; }

    public String getDireccionOrigen() { return direccionOrigen; }
    public void setDireccionOrigen(String direccionOrigen) { this.direccionOrigen = direccionOrigen; }

    public String getDireccionDestino() { return direccionDestino; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
}
