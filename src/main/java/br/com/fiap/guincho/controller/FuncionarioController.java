package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Solicitacao;
import br.com.fiap.guincho.service.FuncionarioService;
import br.com.fiap.guincho.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final SolicitacaoService solicitacaoService;

    public FuncionarioController(FuncionarioService funcionarioService, SolicitacaoService solicitacaoService) {
        this.funcionarioService = funcionarioService;
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping("/sessao")
    public ResponseEntity<Object> login(@RequestBody(required = false) Map<String, String> body) {
        try {
            String pin = body != null ? body.get("pin") : null;
            Map<String, Object> sessao = funcionarioService.login(pin);
            return ResponseEntity.ok(sessao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/solicitacoes")
    public ResponseEntity<Object> listarSolicitacoes(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            funcionarioService.validarSessao(authorization);
            List<Solicitacao> solicitacoes = solicitacaoService.obterTodasSolicitacoes();
            return ResponseEntity.ok(solicitacoes);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/sessao")
    public ResponseEntity<Object> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            funcionarioService.logout(authorization);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
