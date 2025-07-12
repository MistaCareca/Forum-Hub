package com.example.demo.model.usuario;

import com.example.demo.model.perfil.Perfil;
import com.example.demo.model.resposta.Resposta;
import com.example.demo.model.topico.Topico;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "usuario")
@Entity(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private List<Perfil> perfis;

    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;

    @OneToMany(mappedBy = "autor")
    private List<Resposta> respostas;

    public Usuario(DadosUsuario dados) {
        this.id = dados.id();
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = dados.senha();
    }
}