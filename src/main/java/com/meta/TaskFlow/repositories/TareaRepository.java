package com.meta.TaskFlow.repositories;

import com.meta.TaskFlow.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    List<Tarea> findByUsuarioId(Integer usuarioId);
<<<<<<< Updated upstream
    List<Tarea> findByProyectoId(Integer proyectoId);
=======
    List<Tarea> findByProyectoId(Integer proyectoId);
>>>>>>> Stashed changes
}
