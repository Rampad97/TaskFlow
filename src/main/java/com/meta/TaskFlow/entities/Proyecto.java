package com.meta.TaskFlow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "proyecto")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El titulo del proyecto es obligatorio")
    @Size(min = 3, max = 150, message = "El titulo del proyecto debe tener entre 3 y 150 caracteres")
    @Column(unique = true)
    private String titulo;

    @NotNull(message = "Debe asignarse un lider al proyecto")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lider_id", nullable = false)
    private Usuario lider;

    // Contructores
    public Proyecto() {
    }

    public Proyecto(Integer id, String titulo, Usuario lider) {
        this.id = id;
        this.titulo = titulo;
        this.lider = lider;
    }

    // Getters y Setters

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

    public Usuario getLider() {
        return lider;
    }

    public void setLider(Usuario lider) {
        this.lider = lider;
    }
}
