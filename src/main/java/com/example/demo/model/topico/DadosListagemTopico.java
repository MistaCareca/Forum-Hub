package com.example.demo.model.topico;

import com.example.demo.model.curso.Curso;
import com.example.demo.model.usuario.Usuario;
import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        DadosAutorTopico autor,
        DadosCursoTopico curso,
        LocalDateTime dataCriacao,
        String status,
        String respostas
) {
    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor() != null ? new DadosAutorTopico(topico.getAutor()) : null,
                topico.getCurso() != null ? new DadosCursoTopico(topico.getCurso()) : null,
                topico.getDataCriacao(),
                topico.getStatus() != null ? topico.getStatus().name() : "DRAFT",
                "Nenhuma resposta" // Simplificado para evitar lazy loading
        );
    }
}

record DadosAutorTopico(
        Long id,
        String nome,
        String email
) {
    public DadosAutorTopico(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}

record DadosCursoTopico(
        Long id,
        String nome,
        String categoria
) {
    public DadosCursoTopico(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria().name());
    }
}