package com.meta.TaskFlow.controllers;

import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TaskFlow/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    // Crear nuevo usuario
    @PostMapping
    public Usuario createUser(@RequestBody @Valid Usuario usuario) {
        return usuarioService.newUser(usuario);
    }

    // Lista de todos los usuarios
    @GetMapping
    public List<Usuario> allUsers() {
        return  usuarioService.getAllUsers();
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public Optional<Usuario> userById(@PathVariable Integer id) {
        return  usuarioService.getUserById(id);
    }
}
