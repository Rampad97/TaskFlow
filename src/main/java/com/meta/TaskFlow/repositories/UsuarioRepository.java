package com.meta.TaskFlow.repositories;

import com.meta.TaskFlow.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
