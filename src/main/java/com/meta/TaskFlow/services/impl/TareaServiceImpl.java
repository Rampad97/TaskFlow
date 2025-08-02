package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.*;
import com.meta.TaskFlow.exceptions.RecursoNotFoundException;
import com.meta.TaskFlow.repositories.TareaRepository;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import com.meta.TaskFlow.services.interfaces.TareaService;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import com.meta.TaskFlow.util.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TareaServiceImpl implements TareaService {
    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProyectoService proyectoService;

    @Override
    public Tarea newTask(Tarea tarea) {
        Optional<Tarea> existe = tareaRepository.findByTitulo(tarea.getTitulo());
        if (existe.isPresent()) { // Validación de titulo duplicado
            throw new IllegalArgumentException("Ya existe una tarea con ese título.");
        }
        // Validar usuario y proyecto existentes
        Usuario usuario = usuarioService.getUserById(tarea.getUsuario().getId());
        UsuarioValidator.validarMiembro(usuario);

        Proyecto proyecto = proyectoService.getProjectById(tarea.getProyecto().getId());
        tarea.setUsuario(usuario);
        tarea.setProyecto(proyecto);
        // Validar que NO venga fecha de fin (debe ser null)
        LocalDateTime ahora = LocalDateTime.now(); // Validar fecha y hora de inicio
        if (tarea.getFechaInicio().isBefore(ahora)) {
            throw new IllegalArgumentException("La fecha y hora de inicio no deben ser anterior a la actual.");
        }
        if (tarea.getFechaFin() != null) {
            throw new IllegalArgumentException("La fecha y hora final no se establecen al inicio.");
        }
        // Validad prioridad
        String prioridad = tarea.getPrioridad();
        if (prioridad == null || prioridad.isBlank()) {
            throw new IllegalArgumentException("La prioridad es obligatoria: ALTA, MEDIA o BAJA.");
        }
        prioridad = prioridad.toUpperCase();
        if (!prioridad.matches("ALTA|MEDIA|BAJA")) {
            throw new IllegalArgumentException("Prioridad inválida. Usa ALTA, MEDIA o BAJA.");
        }
        tarea.setPrioridad(prioridad);
        // Forzar estado inicial
        tarea.setCompletada(false);
        tarea.setFechaFin(null);

        return tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> getAllTasks() {
        return tareaRepository.findAll();
    }

    @Override
    public Tarea getTaskById(Integer id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Tarea con ID '" + id + "' no encontrada."));
    }

    @Override
    public List<Tarea> getTasksByUserId(Integer usuarioId) {
        List<Tarea> tareas = tareaRepository.findByUsuarioId(usuarioId);
        if (tareas.isEmpty()) {
            throw new RecursoNotFoundException("No se encontraron tareas para el usuario con ID '" + usuarioId + "'.");
        }
        return tareas;
    }

    @Override
    public List<Tarea> getTasksByProjectId(Integer proyectoId) {
        List<Tarea> tareas = tareaRepository.findByProyectoId(proyectoId);
        if (tareas.isEmpty()) {
            throw new RecursoNotFoundException("No se encontraron tareas para el proyecto con ID '" + proyectoId + "'.");
        }
        return tareas;
    }

    @Override
    public void deleteTask(Integer id) {
        if (!tareaRepository.existsById(id)) {
            throw new RecursoNotFoundException("Tarea con ID '" + id + "' no encontrado.");
        }
        tareaRepository.deleteById(id);
    }
}