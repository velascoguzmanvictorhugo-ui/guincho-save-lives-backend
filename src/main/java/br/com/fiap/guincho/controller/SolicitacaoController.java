package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Solicitacao;
import br.com.fiap.guincho.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody Solicitacao solicitacao) {
        try {
            solicitacaoService.adicionar(solicitacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(solicitacao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<Solicitacao>> obterTodasSolicitacoes() {
        return ResponseEntity.ok(solicitacaoService.obterTodasSolicitacoes());
    }
}