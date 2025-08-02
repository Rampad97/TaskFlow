package com.meta.TaskFlow;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.entities.Rol;
import com.meta.TaskFlow.util.UsuarioValidator;

public class TestUsuarioValidator {

    @Test
    void testValidarLider_Valido() {
        Rol rolLider = new Rol();
        rolLider.setId(1);
        Usuario usuario = new Usuario();
        usuario.setId(10);
        usuario.setRol(rolLider);

        assertDoesNotThrow(() -> UsuarioValidator.validarLider(usuario));
    }

    @Test
    void testValidarLider_UsuarioNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UsuarioValidator.validarLider(null));
        assertEquals("El usuario no puede ser nulo y debe tener un ID válido.", ex.getMessage());
    }

    @Test
    void testValidarLider_IdNull() {
        Usuario usuario = new Usuario();
        usuario.setId(null);
        usuario.setRol(new Rol());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UsuarioValidator.validarLider(usuario));
        assertEquals("El usuario no puede ser nulo y debe tener un ID válido.", ex.getMessage());
    }

    @Test
    void testValidarLider_RolNull() {
        Usuario usuario = new Usuario();
        usuario.setId(5);
        usuario.setRol(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UsuarioValidator.validarLider(usuario));
        assertEquals("El usuario debe tener un rol válido.", ex.getMessage());
    }

    @Test
    void testValidarLider_RolNoEsLider() {
        Rol rol = new Rol();
        rol.setId(2); // no es LIDER
        Usuario usuario = new Usuario();
        usuario.setId(3);
        usuario.setRol(rol);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UsuarioValidator.validarLider(usuario));
        assertEquals("El usuario debe tener rol de LIDER.", ex.getMessage());
    }

    @Test
    void testValidarMiembro_Valido() {
        Rol rolMiembro = new Rol();
        rolMiembro.setId(2);
        Usuario usuario = new Usuario();
        usuario.setId(8);
        usuario.setRol(rolMiembro);

        assertDoesNotThrow(() -> UsuarioValidator.validarMiembro(usuario));
    }

    @Test
    void testValidarMiembro_RolEsLider() {
        Rol rolLider = new Rol();
        rolLider.setId(1); // líder
        Usuario usuario = new Usuario();
        usuario.setId(9);
        usuario.setRol(rolLider);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UsuarioValidator.validarMiembro(usuario));
        assertEquals("El usuario no puede tener rol de LIDER.", ex.getMessage());
    }
}
