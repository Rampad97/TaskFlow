package com.meta.TaskFlow.controllers;

import com.meta.TaskFlow.entities.Tarea;
import com.meta.TaskFlow.services.interfaces.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/tareas")
public class TareaController {
    @Autowired
    TareaService tareaService;

    // Crear nueva tarea
    @PostMapping
    public Tarea createTask(@RequestBody Tarea tarea) {
        return tareaService.newTask(tarea);
    }

    // Lista de todas las tareas
    @GetMapping
    public List<Tarea> allTasks() {
        return tareaService.getAllTasks();
    }

    // Buscar tarea por ID
    @GetMapping("/{id}")
    public Optional<Tarea> taskById(@PathVariable Integer id) {
        return tareaService.getTaskById(id);
    }

    // Lista de tareas por ID de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Tarea> taskByUserId(@PathVariable Integer usuarioId) {
        return tareaService.getTasksbyUserId(usuarioId);
    }

    // Lista de tareas por ID de un proyecto
    @GetMapping("/proyecto/{proyectoId}")
    public List<Tarea> taskByProjectId(@PathVariable Integer projectId) {
        return tareaService.getTaskbyProjectId(projectId);
    }

    // Eliminar una tarea por ID
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        tareaService.deleteTask(id);
    }
}
