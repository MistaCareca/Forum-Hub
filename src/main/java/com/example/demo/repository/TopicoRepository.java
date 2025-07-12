package com.example.demo.repository;

import com.example.demo.model.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query("SELECT t FROM topico t JOIN FETCH t.autor JOIN FETCH t.curso")
    Page<Topico> findAllWithAutorAndCurso(Pageable pageable);

    @Query("SELECT t FROM topico t JOIN FETCH t.autor JOIN FETCH t.curso WHERE t.id = :id")
    Optional<Topico> findByIdWithAutorAndCurso(@Param("id") Long id);
}