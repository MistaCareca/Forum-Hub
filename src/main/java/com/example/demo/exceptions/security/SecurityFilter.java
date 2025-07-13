package com.example.demo.exceptions.security;

import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        System.out.println("=== DEBUG SECURITY FILTER ===");
        System.out.println("Token JWT: " + tokenJWT);

        if (tokenJWT != null) {
            var subject = tokenService.validarToken(tokenJWT);
            System.out.println("Subject do token: " + subject);
            if (!subject.isEmpty()) {
                var usuario = usuarioRepository.findUsuarioComPerfis(subject); // Alterado para findUsuarioComPerfis
                System.out.println("Usuário encontrado: " + (usuario != null ? usuario.getEmail() : "null"));
                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    System.out.println("Authorities: " + usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Usuário não encontrado para o subject: " + subject);
                }
            } else {
                System.out.println("Token inválido ou subject vazio");
            }
        } else {
            System.out.println("Nenhum token encontrado no cabeçalho Authorization");
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            System.out.println("Token extraído: " + token);
            return token;
        }
        return null;
    }
}