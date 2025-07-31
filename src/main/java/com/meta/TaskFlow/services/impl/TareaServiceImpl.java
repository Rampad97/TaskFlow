package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.Tarea;
import com.meta.TaskFlow.repositories.TareaRepository;
import com.meta.TaskFlow.services.interfaces.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Tarea> getTasksbyUserId(Integer usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Tarea> getTaskbyProjectId(Integer proyectoId) {
        return tareaRepository.findByProyectId(proyectoId);
    }

    @Override
    public void deleteTask(Integer id) {
        tareaRepository.deleteById(id);
    }
}
