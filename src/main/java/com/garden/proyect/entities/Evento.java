package com.garden.proyect.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria; // CONCIERTO, DEPORTE, TECNOLOGIA

    // Ubicación
    private String lugar;
    private String ciudad;
    private String direccion;

    // Fechas
    private java.time.LocalDateTime fechaEvento;
    private java.time.LocalDateTime fechaCreacion;

    // Capacidad y precio
    private Integer capacidad;
    private Double precioBase;

    // Estado del evento
    private String estado; // ACTIVO, CANCELADO, FINALIZADO

    @ElementCollection
    @CollectionTable(name = "events_photos", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "link")
    private List<String> linkPhoto;

    // Organizador
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario organizador;

    @OneToMany
    @JsonIgnore
    private List<Ticket> listaTickets;
}
