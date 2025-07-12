package com.example.demo.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosUsuario(
        Long id,

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[\\w!@#$%^&*]{5,12}$")
        String senha,

        @NotNull
        Long perfilId
    ) {
}