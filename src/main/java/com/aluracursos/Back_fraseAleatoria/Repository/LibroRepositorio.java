package com.aluracursos.Back_fraseAleatoria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepositorio extends JpaRepository<LibroDB, Long> {
    List<LibroDB> findByTituloEqualsIgnoreCase(String titulo);
    List<LibroDB> findByAutor(String autor);
}