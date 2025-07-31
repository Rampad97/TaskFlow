package com.meta.TaskFlow.services.impl;

import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.repositories.UsuarioRepository;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario newUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUserById(Integer id) {
        return  usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> getUserEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
