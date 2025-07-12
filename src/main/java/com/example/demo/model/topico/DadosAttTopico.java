package com.example.demo.model.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAttTopico(
        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long cursoId
    ) {
}
