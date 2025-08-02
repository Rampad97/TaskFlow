package com.meta.TaskFlow;

import com.meta.TaskFlow.entities.Proyecto;
import com.meta.TaskFlow.entities.Rol;
import com.meta.TaskFlow.entities.Tarea;
import com.meta.TaskFlow.entities.Usuario;
import com.meta.TaskFlow.exceptions.RecursoNotFoundException;
import com.meta.TaskFlow.repositories.TareaRepository;
import com.meta.TaskFlow.services.impl.TareaServiceImpl;
import com.meta.TaskFlow.services.interfaces.ProyectoService;
import com.meta.TaskFlow.services.interfaces.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestTareaService {
    @Mock
    TareaRepository tareaRepository;
    @Mock
    UsuarioService usuarioService;
    @Mock
    ProyectoService proyectoService;
    @InjectMocks
    TareaServiceImpl tareaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testNewTask_Success() {
        Rol rolMiembro = new Rol();
        rolMiembro.setId(2);  // ← diferente de 1 para que no sea líder
        rolMiembro.setRol("MIEMBRO");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Mario");
        usuario.setRol(rolMiembro);  // ← ¡necesario!

        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setTitulo("Proyecto X");

        Tarea tarea = new Tarea();
        tarea.setTitulo("Crear login");
        tarea.setUsuario(usuario);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad("MEDIA");
        tarea.setCompletada(false);
        tarea.setFechaInicio(LocalDateTime.now().plusDays(1));

        when(tareaRepository.findByTitulo("Crear login")).thenReturn(Optional.empty());
        when(usuarioService.getUserById(1)).thenReturn(usuario);
        when(proyectoService.getProjectById(1)).thenReturn(proyecto);
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea creada = tareaService.newTask(tarea);

        assertEquals("Crear login", creada.getTitulo());
        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    void testNewTask_TituloDuplicado_ThrowsException() {
        Tarea tarea = new Tarea();
        tarea.setTitulo("Crear login");
        when(tareaRepository.findByTitulo("Crear login")).thenReturn(Optional.of(tarea));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.newTask(tarea);
        });
        assertEquals("Ya existe una tarea con ese título.", exception.getMessage());
    }

    @Test
    void testGetAllTasks() {
        when(tareaRepository.findAll()).thenReturn(List.of(new Tarea(), new Tarea()));
        List<Tarea> tareas = tareaService.getAllTasks();
        assertEquals(2, tareas.size());
    }

    @Test
    void testGetTaskById_Found() {
        Tarea tarea = new Tarea();
        tarea.setId(1);
        tarea.setTitulo("Revisar errores");
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
        Tarea resultado = tareaService.getTaskById(1);
        assertEquals("Revisar errores", resultado.getTitulo());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(tareaRepository.findById(404)).thenReturn(Optional.empty());
        RecursoNotFoundException exception = assertThrows(RecursoNotFoundException.class, () -> {
            tareaService.getTaskById(404);
        });
        assertEquals("Tarea con ID '404' no encontrada.", exception.getMessage());
    }

    @Test
    void testDeleteTask_Success() {
        when(tareaRepository.existsById(3)).thenReturn(true);
        doNothing().when(tareaRepository).deleteById(3);
        tareaService.deleteTask(3);
        verify(tareaRepository).deleteById(3);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(tareaRepository.existsById(99)).thenReturn(false);
        RecursoNotFoundException exception = assertThrows(RecursoNotFoundException.class, () -> {
            tareaService.deleteTask(99);
        });
        assertEquals("Tarea con ID '99' no encontrado.", exception.getMessage());
    }

    @Test
    void testGetTasksByUserId_TareasEncontradas() {
        Tarea t1 = new Tarea();
        Tarea t2 = new Tarea();
        when(tareaRepository.findByUsuarioId(1)).thenReturn(List.of(t1, t2));
        List<Tarea> resultado = tareaService.getTasksByUserId(1);
        assertEquals(2, resultado.size());
        verify(tareaRepository).findByUsuarioId(1);
    }

    @Test
    void testGetTasksByUserId_NoTareas() {
        when(tareaRepository.findByUsuarioId(99)).thenReturn(List.of());
        RecursoNotFoundException ex = assertThrows(RecursoNotFoundException.class,
                () -> tareaService.getTasksByUserId(99));
        assertEquals("No se encontraron tareas para el usuario con ID '99'.", ex.getMessage());
    }

    @Test
    void testGetTasksByProjectId_TareasEncontradas() {
        Tarea t1 = new Tarea();
        when(tareaRepository.findByProyectoId(5)).thenReturn(List.of(t1));
        List<Tarea> resultado = tareaService.getTasksByProjectId(5);
        assertEquals(1, resultado.size());
        verify(tareaRepository).findByProyectoId(5);
    }

    @Test
    void testGetTasksByProjectId_NoTareas() {
        when(tareaRepository.findByProyectoId(200)).thenReturn(List.of());
        RecursoNotFoundException ex = assertThrows(RecursoNotFoundException.class,
                () -> tareaService.getTasksByProjectId(200));
        assertEquals("No se encontraron tareas para el proyecto con ID '200'.", ex.getMessage());
    }

    @Test
    void testNewTask_PrioridadNull_ThrowsException() {
        Rol rolMiembro = new Rol();
        rolMiembro.setId(2);
        rolMiembro.setRol("MIEMBRO");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Mario");
        usuario.setRol(rolMiembro);

        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setTitulo("Proyecto X");

        Tarea tarea = new Tarea();
        tarea.setTitulo("TareaPrioridadNull");
        tarea.setUsuario(usuario);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad(null);
        tarea.setFechaInicio(LocalDateTime.now().plusDays(1));
        tarea.setFechaFin(null);

        when(tareaRepository.findByTitulo("TareaPrioridadNull")).thenReturn(Optional.empty());
        when(usuarioService.getUserById(1)).thenReturn(usuario);
        when(proyectoService.getProjectById(1)).thenReturn(proyecto);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.newTask(tarea);
        });
        assertEquals("La prioridad es obligatoria: ALTA, MEDIA o BAJA.", ex.getMessage());
    }

    @Test
    void testNewTask_PrioridadInvalida_ThrowsException() {
        Rol rolMiembro = new Rol();
        rolMiembro.setId(2);
        rolMiembro.setRol("MIEMBRO");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Mario");
        usuario.setRol(rolMiembro);

        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setTitulo("Proyecto X");

        Tarea tarea = new Tarea();
        tarea.setTitulo("TareaPrioridadInvalida");
        tarea.setUsuario(usuario);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad("URGENTE");
        tarea.setFechaInicio(LocalDateTime.now().plusDays(1));
        tarea.setFechaFin(null);

        when(tareaRepository.findByTitulo("TareaPrioridadInvalida")).thenReturn(Optional.empty());
        when(usuarioService.getUserById(1)).thenReturn(usuario);
        when(proyectoService.getProjectById(1)).thenReturn(proyecto);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.newTask(tarea);
        });
        assertEquals("Prioridad inválida. Usa ALTA, MEDIA o BAJA.", ex.getMessage());
    }

    @Test
    void testNewTask_FechaInicioAnterior_ThrowsException() {
        Rol rolMiembro = new Rol();
        rolMiembro.setId(2);
        rolMiembro.setRol("MIEMBRO");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Mario");
        usuario.setRol(rolMiembro);

        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setTitulo("Proyecto X");

        Tarea tarea = new Tarea();
        tarea.setTitulo("TareaFechaInicioAnterior");
        tarea.setUsuario(usuario);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad("MEDIA");
        tarea.setFechaInicio(LocalDateTime.now().minusHours(1)); // antes de ahora
        tarea.setFechaFin(null);

        when(tareaRepository.findByTitulo("TareaFechaInicioAnterior")).thenReturn(Optional.empty());
        when(usuarioService.getUserById(1)).thenReturn(usuario);
        when(proyectoService.getProjectById(1)).thenReturn(proyecto);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.newTask(tarea);
        });
        assertEquals("La fecha y hora de inicio no deben ser anterior a la actual.", ex.getMessage());
    }

    @Test
    void testNewTask_FechaFinNoNull_ThrowsException() {
        Rol rolMiembro = new Rol();
        rolMiembro.setId(2);
        rolMiembro.setRol("MIEMBRO");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Mario");
        usuario.setRol(rolMiembro);

        Proyecto proyecto = new Proyecto();
        proyecto.setId(1);
        proyecto.setTitulo("Proyecto X");

        Tarea tarea = new Tarea();
        tarea.setTitulo("TareaFechaFinNoNull");
        tarea.setUsuario(usuario);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad("ALTA");
        tarea.setFechaInicio(LocalDateTime.now().plusDays(1));
        tarea.setFechaFin(LocalDateTime.now());

        when(tareaRepository.findByTitulo("TareaFechaFinNoNull")).thenReturn(Optional.empty());
        when(usuarioService.getUserById(1)).thenReturn(usuario);
        when(proyectoService.getProjectById(1)).thenReturn(proyecto);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.newTask(tarea);
        });
        assertEquals("La fecha y hora final no se establecen al inicio.", ex.getMessage());
    }
}