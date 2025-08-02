package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario newUser(Usuario usuario);
    List<Usuario> getAllUsers();
    Usuario getUserById(Integer id);
}
