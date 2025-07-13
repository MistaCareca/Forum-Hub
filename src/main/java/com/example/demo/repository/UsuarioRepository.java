package com.example.demo.repository;

import com.example.demo.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    @Query("SELECT DISTINCT u FROM usuario u LEFT JOIN FETCH u.perfis WHERE u.email = :email")
    Usuario findUsuarioComPerfis(@Param("email") String email);
}