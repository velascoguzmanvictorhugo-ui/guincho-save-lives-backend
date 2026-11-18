package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Solicitacao;
import br.com.fiap.guincho.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody Solicitacao solicitacao) {
        try {
            solicitacaoService.adicionar(solicitacao);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(solicitacao);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Solicitacao>> obterTodasSolicitacoes() {
        // Al usar List en lugar de ArrayList, el error de compilación de Maven desaparece
        return ResponseEntity.ok(solicitacaoService.obterTodasSolicitacoes());
    }
}