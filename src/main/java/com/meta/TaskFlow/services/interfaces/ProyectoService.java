package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Proyecto;

import java.util.List;

public interface ProyectoService {
    Proyecto newProject(Proyecto proyecto);
    List<Proyecto> getAllProjects();
    Proyecto getProjectById(Integer id);
    List<Proyecto> getProjectsByLeaderId(Integer liderId);
}
