package com.example.demo.model.topico;

import com.example.demo.model.curso.Curso;
import com.example.demo.model.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosTopico(
        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,

        @NotNull
        @Valid
        Curso curso,

        @NotNull
        @Valid
        Usuario autor
) {}