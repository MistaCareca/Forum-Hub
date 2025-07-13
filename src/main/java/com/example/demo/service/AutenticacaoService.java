package com.example.demo.service;

import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== DEBUG AUTENTICACAO SERVICE ===");
        System.out.println("Buscando usuário: " + username);

        try {
            var usuario = repository.findUsuarioComPerfis(username);

            if (usuario == null) {
                System.out.println("Usuário não encontrado: " + username);
                throw new UsernameNotFoundException("Usuário não encontrado: " + username);
            }

            System.out.println("Usuário encontrado:");
            System.out.println("- ID: " + usuario.getId());
            System.out.println("- Nome: " + usuario.getNome());
            System.out.println("- Email: " + usuario.getEmail());
            System.out.println("- Senha (hash): " + usuario.getSenha());
            System.out.println("- Perfis: " + usuario.getPerfis());
            System.out.println("- Authorities: " + usuario.getAuthorities());

            return usuario;

        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            e.printStackTrace();
            throw new UsernameNotFoundException("Erro ao buscar usuário: " + username, e);
        }
    }
}