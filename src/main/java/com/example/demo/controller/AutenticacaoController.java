package com.example.demo.controller;

import com.example.demo.exceptions.security.TokenService;
import com.example.demo.model.usuario.DadosAutenticacao;
import com.example.demo.model.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            System.out.println("=== DEBUG LOGIN ===");
            System.out.println("Login recebido: " + dados.login());
            System.out.println("Senha recebida: " + dados.senha());

            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            System.out.println("Token de autenticação criado: " + authenticationToken);

            var authentication = manager.authenticate(authenticationToken);
            System.out.println("Autenticação bem-sucedida: " + authentication);
            System.out.println("Principal: " + authentication.getPrincipal());

            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
            System.out.println("Token JWT gerado: " + tokenJWT);

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

        } catch (AuthenticationException e) {
            System.out.println("=== ERRO DE AUTENTICAÇÃO ===");
            System.out.println("Tipo do erro: " + e.getClass().getSimpleName());
            System.out.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Credenciais inválidas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("=== ERRO GERAL ===");
            System.out.println("Tipo do erro: " + e.getClass().getSimpleName());
            System.out.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

    public record DadosTokenJWT(String token) {}
}