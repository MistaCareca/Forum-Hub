package com.example.demo.model.perfil;

import com.example.demo.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Table(name = "perfil")
@Entity(name = "perfil")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios;
}
