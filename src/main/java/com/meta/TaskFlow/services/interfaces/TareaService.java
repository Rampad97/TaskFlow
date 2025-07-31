package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    Tarea newTask(Tarea tarea);
    List<Tarea> getAllTasks();
    Optional<Tarea> getTaskById(Integer id);
    List<Tarea> getTasksbyUserId(Integer usuarioId);
    List<Tarea> getTaskbyProjectId(Integer proyectoId);
    void deleteTask(Integer id);
}
