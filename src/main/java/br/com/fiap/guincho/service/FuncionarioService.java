package br.com.fiap.guincho.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FuncionarioService {

    private final Map<String, Instant> sessoesAtivas = new HashMap<>();

    public Map<String, Object> login(String pin) {
        String pinCorreto = System.getenv("EMPLOYEE_PIN");
        if (pinCorreto == null || pinCorreto.isBlank()) {
            pinCorreto = "12345678";
        }

        if (pin == null || !pin.equals(pinCorreto)) {
            throw new IllegalArgumentException("PIN inválido.");
        }

        String token = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now().plusSeconds(1800);
        sessoesAtivas.put(token, expiresAt);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("token", token);
        resposta.put("expiresAt", expiresAt);
        return resposta;
    }

    public void validarSessao(String authorization) {
        String token = extrairToken(authorization);
        Instant expiresAt = sessoesAtivas.get(token);

        if (expiresAt == null || expiresAt.isBefore(Instant.now())) {
            sessoesAtivas.remove(token);
            throw new SecurityException("Sessão inválida ou expirada.");
        }
    }

    public void logout(String authorization) {
        String token = extrairToken(authorization);
        sessoesAtivas.remove(token);
    }

    private String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new SecurityException("Token não informado.");
        }
        return authorization.substring(7);
    }
}