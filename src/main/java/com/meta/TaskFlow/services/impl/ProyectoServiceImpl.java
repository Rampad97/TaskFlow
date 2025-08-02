package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.Proyecto;
import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.exceptions.RecursoNotFoundException;
import com.meta.TaskFlow.repositories.ProyectoRepository;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import com.meta.TaskFlow.util.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {
    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public Proyecto newProject(Proyecto proyecto) {
        Optional<Proyecto> existe = proyectoRepository.findByTitulo(proyecto.getTitulo());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("Ya existe un proyecto con ese nombre.");
        }

        Usuario lider = usuarioService.getUserById(proyecto.getLider().getId());
        UsuarioValidator.validarLider(lider);
        proyecto.setLider(lider);

        return proyectoRepository.save(proyecto);
    }

    @Override
    public List<Proyecto> getAllProjects() {
        return proyectoRepository.findAll();
    }

    @Override
    public List<Proyecto> getProjectsByLeaderId(Integer liderId) {
        List<Proyecto> proyectos = proyectoRepository.findByLiderId(liderId);
        if (proyectos.isEmpty()) {
            throw new RecursoNotFoundException("No se encontraron proyectos para el usuario con ID '" + liderId + "'.");
        }
        return proyectos;
    }

    @Override
    public Proyecto getProjectById(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Proyecto con ID: '" + id + "' no encontrado."));
    }
}
