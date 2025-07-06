package com.example.demo.topico;

import com.example.demo.curso.Curso;
import com.example.demo.usuario.Usuario;

public record DadosTopico(String titulo, String mensagem, Usuario autor, Curso curso, String respostas) {

}