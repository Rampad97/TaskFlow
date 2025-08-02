package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.exceptions.RecursoNotFoundException;
import com.meta.TaskFlow.repositories.RolRepository;
import com.meta.TaskFlow.repositories.UsuarioRepository;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Override
    public Usuario newUser(Usuario usuario) {
        // Validación completa del rol
        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
            throw new IllegalArgumentException("Debe asignarse un rol válido (no nulo ni vacío).");
        }

        Integer rolId = usuario.getRol().getId();
        if (!rolRepository.existsById(rolId)) {
            throw new IllegalArgumentException("El rol con ID " + rolId + " no existe.");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUserById(Integer id) {
        return  usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario con ID: '" + id + "' no encontrado."));
    }
}
