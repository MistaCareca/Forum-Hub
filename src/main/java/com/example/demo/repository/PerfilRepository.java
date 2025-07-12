package com.example.demo.repository;

import com.example.demo.model.perfil.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
