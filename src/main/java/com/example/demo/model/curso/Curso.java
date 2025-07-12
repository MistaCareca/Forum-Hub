package com.example.demo.model.curso;

import com.example.demo.model.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "curso")
@Entity(name = "curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", columnDefinition = "VARCHAR(50)", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private List<Topico> topicos;

    public Curso(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}