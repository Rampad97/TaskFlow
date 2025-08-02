package com.meta.TaskFlow.controllers;

import com.meta.TaskFlow.entities.Proyecto;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TaskFlow/proyectos")
public class ProyectoController {
    @Autowired
    ProyectoService proyectoService;

    // Crear nuevo proyecto
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProject(@RequestBody @Valid Proyecto proyecto) {
        Proyecto p = proyectoService.newProject(proyecto);
        Map<String, Object> response = new HashMap<>();
        response.put("id", p.getId());
        response.put("nombre", p.getTitulo());
        response.put("mensaje", "Proyecto creado existosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lista de todos los proyectos
    @GetMapping
    public ResponseEntity<List<Proyecto>> allProjects() {
        List<Proyecto> proyectos = proyectoService.getAllProjects();
        return ResponseEntity.ok(proyectos);
    }

    // Buscar proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> projectById(@PathVariable Integer id) {
        Proyecto proyecto = proyectoService.getProjectById(id);
        return ResponseEntity.ok(proyecto);
    }
}
