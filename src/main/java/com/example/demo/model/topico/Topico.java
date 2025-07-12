package com.example.demo.model.topico;

import com.example.demo.model.curso.Curso;
import com.example.demo.model.resposta.Resposta;
import com.example.demo.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topico")
@Entity(name = "topico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Resposta> respostas;

    public Topico(String titulo, String mensagem, Curso curso, Usuario autor) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.curso = curso;
        this.autor = autor;
        this.status = Status.DRAFT;
    }

    public void atualizarDados(String titulo, String mensagem, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.curso = curso;
    }
}