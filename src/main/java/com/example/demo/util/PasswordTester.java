package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordTester implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Teste com a senha e hash do seu log
        String rawPassword = "admin123";
        String hashedPassword = "$2a$10$Kn5.9hpZhPWJBUCd8JGaAOzQQU4KgfcBpPNVNGWPW7.jSGCnJYWNm";

        System.out.println("=== TESTE DE SENHA ===");
        System.out.println("Senha original: " + rawPassword);
        System.out.println("Hash do banco: " + hashedPassword);

        boolean matches = passwordEncoder.matches(rawPassword, hashedPassword);
        System.out.println("Senha confere: " + matches);

        // Gera um novo hash para comparação
        String newHash = passwordEncoder.encode(rawPassword);
        System.out.println("Novo hash gerado: " + newHash);

        // Testa o novo hash
        boolean newMatches = passwordEncoder.matches(rawPassword, newHash);
        System.out.println("Novo hash confere: " + newMatches);
        System.out.println("========================");
    }
}