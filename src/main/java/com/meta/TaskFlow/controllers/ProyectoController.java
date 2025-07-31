package com.meta.TaskFlow.controllers;

import com.meta.TaskFlow.entities.Proyecto;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/proyectos")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;

    // Crear nuevo proyecto
    @PostMapping
    public Proyecto createProject(@RequestBody Proyecto proyecto) {
        return proyectoService.newProject(proyecto);
    }

    // Lista de todos los proyectos
    @GetMapping
    public List<Proyecto> allProjects() {
        return proyectoService.getAllProjects();
    }

    // Buscar proyecto por ID
    @GetMapping("/{id}")
    public Optional<Proyecto> projectById(@PathVariable Integer id) {
        return proyectoService.getProjectById(id);
    }

    // Eliminar proyecto por ID
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Integer id) {
        proyectoService.deleteProject(id);
    }
}
