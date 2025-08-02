package com.meta.TaskFlow;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.meta.TaskFlow.entities.Rol;
import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.exceptions.RecursoNotFoundException;
import com.meta.TaskFlow.repositories.RolRepository;
import com.meta.TaskFlow.repositories.UsuarioRepository;
import com.meta.TaskFlow.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class TestUsuarioService {
    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    RolRepository rolRepository;

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testNewUser_Success() {
        Rol rol = new Rol();
        rol.setId(1);
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setRol(rol);
        when(rolRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario resultado = usuarioService.newUser(usuario);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }
    @Test
    void testNewUser_RolNull_ThrowsException() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Pedro");
        usuario.setRol(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.newUser(usuario);
        });
        assertEquals("Debe asignarse un rol válido (no nulo ni vacío).", exception.getMessage());
    }

    @Test
    void testNewUser_RolDoesNotExist_ThrowsException() {
        Rol rol = new Rol();
        rol.setId(5);
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");
        usuario.setRol(rol);
        when(rolRepository.existsById(5)).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.newUser(usuario);
        });
        assertEquals("El rol con ID 5 no existe.", exception.getMessage());
    }
    @Test
    void testGetAllUsers() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));
        List<Usuario> usuarios = usuarioService.getAllUsers();
        assertEquals(2, usuarios.size());
    }
    @Test
    void testGetUserById_Found() {
        Usuario usuario = new Usuario();
        usuario.setId(10);
        usuario.setNombre("Lucas");

        when(usuarioRepository.findById(10)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUserById(10);
        assertEquals("Lucas", resultado.getNombre());
    }
    @Test
    void testGetUserById_NotFound() {
        when(usuarioRepository.findById(20)).thenReturn(Optional.empty());
        RecursoNotFoundException exception = assertThrows(RecursoNotFoundException.class, () -> {
            usuarioService.getUserById(20);
        });
        assertEquals("Usuario con ID: '20' no encontrado.", exception.getMessage());
    }
}