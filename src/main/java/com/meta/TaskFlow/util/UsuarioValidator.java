package com.meta.TaskFlow.util;

import com.meta.TaskFlow.entities.Usuario;

public class UsuarioValidator {
    public static void validarLider(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo y debe tener un ID válido.");
        }
        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un rol válido.");
        }
        if (!usuario.getRol().getId().equals(1)) {
            throw new IllegalArgumentException("El usuario debe tener rol de LIDER.");
        }
    }

    public static void validarMiembro(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo y debe tener un ID válido.");
        }
        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un rol válido.");
        }
        if (usuario.getRol().getId().equals(1)) {
            throw new IllegalArgumentException("El usuario no puede tener rol de LIDER.");
        }
    }
}
