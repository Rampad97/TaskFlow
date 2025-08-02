package com.meta.TaskFlow.controllers;

import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TaskFlow/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody @Valid Usuario usuario) {
        Usuario u = usuarioService.newUser(usuario);
        Map<String, Object> response = new HashMap<>();
        response.put("id", u.getId());
        response.put("nombre", u.getNombre());
        response.put("mensaje", "Usuario creado existosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Lista de todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> allUsers() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> userById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUserById(id);
        return ResponseEntity.ok(usuario);
    }
}
