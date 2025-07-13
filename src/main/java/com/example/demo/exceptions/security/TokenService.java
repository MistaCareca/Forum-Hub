package com.example.demo.exceptions.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret:minha-chave-secreta-super-segura-123}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validarToken(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algoritmo)
                    .withIssuer("auth0")
                    .build();
            var decodedJWT = verifier.verify(tokenJWT);
            System.out.println("Token válido. Subject: " + decodedJWT.getSubject());
            System.out.println("Expiração: " + decodedJWT.getExpiresAt());
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println("=== ERRO NA VALIDAÇÃO DO TOKEN ===");
            System.out.println("Mensagem: " + exception.getMessage());
            exception.printStackTrace();
            return "";
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}