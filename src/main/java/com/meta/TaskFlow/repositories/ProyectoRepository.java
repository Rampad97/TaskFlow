package com.meta.TaskFlow.repositories;

import com.meta.TaskFlow.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByLiderId(Integer liderId);
    Optional<Proyecto> findByTitulo(String titulo);
}
