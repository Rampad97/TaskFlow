package com.meta.TaskFlow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El titulo de la tarea es obligatorio")
    @Size(min = 3, max = 150, message = "El titulo de la tarea debe tener entre 3 y 150 caracteres")
    private String titulo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser anterior a la actual")
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @NotBlank(message = "La prioridad es obligatoria")
    @Pattern(regexp = "ALTA|MEDIA|BAJA", message = "Prioridad debe ser ALTA, MEDIA o BAJA")
    private String prioridad;

    @Column(nullable = false)
    private boolean completada;

    @NotNull(message = "La tarea debe estar asignada a un usuario")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull(message = "La tarea debe estar asociada a un proyecto")
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @Transient //
    private Long horasInvertidas;

    // Constructores
    public Tarea() {
    }

    public Tarea(Integer id, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFin, String prioridad, boolean completada, Usuario usuario, Proyecto proyecto, Long horasInvertidas) {
        this.id = id;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.prioridad = prioridad;
        this.completada = completada;
        this.usuario = usuario;
        this.proyecto = proyecto;
        this.horasInvertidas = horasInvertidas;
    }

// Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Long getHorasInvertidas() {
        if (fechaInicio != null && fechaFin != null) {
            return java.time.Duration.between(fechaInicio, fechaFin).toHours();
        }
        return 0L;
    }

    public void setHorasInvertidas(Long horasInvertidas) {
        this.horasInvertidas = horasInvertidas;
    }
}