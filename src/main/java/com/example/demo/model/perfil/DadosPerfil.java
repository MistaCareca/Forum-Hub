package com.example.demo.model.perfil;

import jakarta.validation.constraints.NotBlank;

public record DadosPerfil(
        Long id,

        @NotBlank
        String nome
    ) {
}
