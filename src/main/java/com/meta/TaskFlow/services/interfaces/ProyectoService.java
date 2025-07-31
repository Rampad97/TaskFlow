package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {
    Proyecto newProject(Proyecto proyecto);
    List<Proyecto> getAllProjects();
    Optional<Proyecto> getProjectById(Integer id);
    void deleteProject(Integer id);
}
