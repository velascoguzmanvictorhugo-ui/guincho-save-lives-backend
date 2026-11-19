package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Solicitacao;
import br.com.fiap.guincho.exception.RegraDeNegocioException;
import br.com.fiap.guincho.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao cadastrar solicitação.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> obterTodasSolicitacoes() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.obterTodasSolicitacoes();
            return ResponseEntity.ok(solicitacoes);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao listar solicitações.");
        }
    }
}