package com.example.demo.model.resposta;

import com.example.demo.model.topico.Topico;
import com.example.demo.model.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosResposta(
        Long id,

        @NotBlank
        String mensagem,

        @NotNull
        @Valid
        Usuario autor,

        @NotNull
        Topico topico,

        Boolean solucao

) {}
