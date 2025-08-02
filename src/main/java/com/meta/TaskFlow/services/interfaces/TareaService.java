package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Tarea;

import java.util.List;

public interface TareaService {
    Tarea newTask(Tarea tarea);
    List<Tarea> getAllTasks();
    Tarea getTaskById(Integer id);
    List<Tarea> getTasksByUserId(Integer usuarioId);
    List<Tarea> getTasksByProjectId(Integer proyectoId);
    void deleteTask(Integer id);
}
