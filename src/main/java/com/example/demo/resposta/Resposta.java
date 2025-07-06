package com.example.demo.resposta;

import com.example.demo.resposta.DadosResposta;
import com.example.demo.topico.Topico;
import com.example.demo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "resposta")
@Entity(name = "resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private Boolean solucao = false;

    public Resposta(DadosResposta dados) {
        this.mensagem = dados.mensagem();
        this.topico = dados.topico();
        this.autor = dados.autor();
        this.solucao = dados.solucao() != null ? dados.solucao() : false;
        this.dataCriacao = LocalDateTime.now();
    }
}