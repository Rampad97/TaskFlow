package com.meta.TaskFlow.services.interfaces;

import com.meta.TaskFlow.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario newUser(Usuario usuario);
    List<Usuario> getAllUsers();
    Optional<Usuario> getUserById(Integer id);
    Optional<Usuario> getUserEmail(String email);
}
