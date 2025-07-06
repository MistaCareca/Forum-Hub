package com.example.demo.resposta;

import com.example.demo.topico.Topico;
import com.example.demo.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosResposta(Long id, String mensagem, Usuario autor, Topico topico, Boolean solucao, LocalDateTime dataCriacao) {
}
