package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Estado;
import com.meta.TaskFlow.entities.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    Tarea newTask(Tarea tarea);
    List<Tarea> getAllTasks();
    Optional<Tarea> getTaskById(Integer id);
    List<Tarea> getTasksByUserId(Integer usuarioId);
    List<Tarea> getTaskByProjectId(Integer proyectoId);
    void deleteTask(Integer id);
    Tarea updateStatus(Integer tareaId, Estado nevoEstado);
}
