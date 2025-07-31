package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.Estado;
import com.meta.TaskFlow.entities.Tarea;
import com.meta.TaskFlow.repositories.TareaRepository;
import com.meta.TaskFlow.services.interfaces.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.util.ElementScanner6;
import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {
    @Autowired
    TareaRepository tareaRepository;

    @Override
    public Tarea newTask(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> getAllTasks() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> getTaskById(Integer id) {
        return tareaRepository.findById(id);
    }

    @Override
    public List<Tarea> getTasksByUserId(Integer usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Tarea> getTaskByProjectId(Integer proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
    }

    @Override
    public void deleteTask(Integer id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public Tarea updateStatus(Integer tareaId, Estado nuevoEstado) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(()-> new IllegalArgumentException("Tarea con ID: " + tareaId + " no encontrada"));
        tarea.setEstado(nuevoEstado);
        return tareaRepository.save(tarea);
    }
}
