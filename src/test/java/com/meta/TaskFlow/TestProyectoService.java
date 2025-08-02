package com.meta.TaskFlow;

import com.meta.TaskFlow.entities.Proyecto;
import com.meta.TaskFlow.entities.Rol;
import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.exceptions.RecursoNotFoundException;
import com.meta.TaskFlow.repositories.ProyectoRepository;
import com.meta.TaskFlow.services.impl.ProyectoServiceImpl;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class TestProyectoService {
    @Mock
    ProyectoRepository proyectoRepository;
    @Mock
    UsuarioService usuarioService;
    @InjectMocks
    ProyectoServiceImpl proyectoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testNewProject_Success() {
        Rol rolLider = new Rol();
        rolLider.setId(1);
        rolLider.setRol("LIDER");

        Usuario lider = new Usuario();
        lider.setId(1);
        lider.setNombre("Carlos");
        lider.setRol(rolLider);

        Proyecto proyecto = new Proyecto();
        proyecto.setTitulo("Sistema Inventario");
        proyecto.setLider(lider);

        when(proyectoRepository.findByTitulo("Sistema Inventario")).thenReturn(Optional.empty());
        when(usuarioService.getUserById(1)).thenReturn(lider);
        when(proyectoRepository.save(proyecto)).thenReturn(proyecto);

        Proyecto creado = proyectoService.newProject(proyecto);

        assertEquals("Sistema Inventario", creado.getTitulo());
        assertEquals("Carlos", creado.getLider().getNombre());
        verify(proyectoRepository, times(1)).save(proyecto);
    }


    @Test
    void testNewProject_TituloDuplicado_ThrowsException() {
        Proyecto proyecto = new Proyecto();
        proyecto.setTitulo("App Finanzas");

        when(proyectoRepository.findByTitulo("App Finanzas")).thenReturn(Optional.of(proyecto));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            proyectoService.newProject(proyecto);
        });

        assertEquals("Ya existe un proyecto con ese nombre.", exception.getMessage());
    }

    @Test
    void testGetAllProjects() {
        when(proyectoRepository.findAll()).thenReturn(List.of(new Proyecto(), new Proyecto()));

        List<Proyecto> lista = proyectoService.getAllProjects();
        assertEquals(2, lista.size());
    }

    @Test
    void testGetProjectById_Found() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setTitulo("API Gestión");

        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyecto));

        Proyecto resultado = proyectoService.getProjectById(1);
        assertEquals("API Gestión", resultado.getTitulo());
    }

    @Test
    void testGetProjectById_NotFound() {
        when(proyectoRepository.findById(99)).thenReturn(Optional.empty());

        RecursoNotFoundException exception = assertThrows(RecursoNotFoundException.class, () -> {
            proyectoService.getProjectById(99);
        });

        assertEquals("Proyecto con ID: '99' no encontrado.", exception.getMessage());
    }

    @Test
    void testGetProjectsByLeaderId_Found() {
        Usuario lider = new Usuario();
        lider.setId(5);
        Proyecto proyecto = new Proyecto();
        proyecto.setTitulo("Inventario Web");

        when(proyectoRepository.findByLiderId(5)).thenReturn(List.of(proyecto));

        List<Proyecto> proyectos = proyectoService.getProjectsByLeaderId(5);
        assertEquals(1, proyectos.size());
    }

    @Test
    void testGetProjectsByLeaderId_NotFound() {
        when(proyectoRepository.findByLiderId(88)).thenReturn(List.of());

        RecursoNotFoundException exception = assertThrows(RecursoNotFoundException.class, () -> {
            proyectoService.getProjectsByLeaderId(88);
        });

        assertEquals("No se encontraron proyectos para el usuario con ID '88'.", exception.getMessage());
    }
}
