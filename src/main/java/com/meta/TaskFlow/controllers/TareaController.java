package com.meta.TaskFlow.controllers;

import com.meta.TaskFlow.entities.Tarea;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import com.meta.TaskFlow.services.interfaces.TareaService;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TaskFlow/tareas")
public class TareaController {
    @Autowired
    TareaService tareaService;

    @Autowired
    UsuarioService usuarioService;  // Validar existencia usuario

    @Autowired
    ProyectoService proyectoService; // Validar existencia proyecto

    // Crear nueva tarea
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody @Valid Tarea tarea) {
        Tarea t = tareaService.newTask(tarea);
        Map<String, Object> response = new HashMap<>();
        response.put("id", t.getId());
        response.put("titulo", t.getTitulo());
        response.put("mensaje", "Tarea creada existosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lista de todas las tareas
    @GetMapping
    public ResponseEntity<List<Tarea>> allTasks() {
        List<Tarea> tareas = tareaService.getAllTasks();
        return ResponseEntity.ok(tareas);
    }

    // Buscar tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> taskById(@PathVariable Integer id) {
        Tarea tarea = tareaService.getTaskById(id);
        return ResponseEntity.ok(tarea);
    }

    // Lista de tareas por ID de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Tarea>> taskByUserId(@PathVariable Integer usuarioId) {
        usuarioService.getUserById(usuarioId);
        List<Tarea> tareas = tareaService.getTasksByUserId(usuarioId);
        return ResponseEntity.ok(tareas);
    }

    // Lista de tareas por ID de un proyecto
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Tarea>> taskByProjectId(@PathVariable Integer proyectoId) {
        proyectoService.getProjectById(proyectoId);
        List<Tarea> tareas = tareaService.getTasksByProjectId(proyectoId);
        return ResponseEntity.ok(tareas);
    }

    // Eliminar una tarea por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        tareaService.deleteTask(id);
        return ResponseEntity.ok("Tarea eliminada correctamente.");
    }
}
