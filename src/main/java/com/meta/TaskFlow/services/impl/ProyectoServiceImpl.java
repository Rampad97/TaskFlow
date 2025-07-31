package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.Proyecto;
import com.meta.TaskFlow.repositories.ProyectoRepository;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {
    @Autowired
    ProyectoRepository proyectoRepository;

    @Override
    public Proyecto newProject(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public List<Proyecto> getAllProjects() {
        return proyectoRepository.findAll();
    }

    @Override
    public Optional<Proyecto> getProjectById(Integer id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public void deleteProject(Integer id) {
        proyectoRepository.deleteById(id);
    }
}
