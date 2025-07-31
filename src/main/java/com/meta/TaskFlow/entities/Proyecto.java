package com.meta.TaskFlow.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "lider_id")
    private Usuario lider;

    // Contructores

    public Proyecto() {
    }

    public Proyecto(Integer id, String nombre, String descripcion, Usuario lider) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lider = lider;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getLider() {
        return lider;
    }

    public void setLider(Usuario lider) {
        this.lider = lider;
    }
}
