package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Correio;
import br.com.fiap.guincho.dominio.GeradorDeRelatorio;
import br.com.fiap.guincho.dominio.Mensageiro;
import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import br.com.fiap.guincho.infra.arquivo.RelacaoDeChamadosEmArquivo;
import br.com.fiap.guincho.infra.dao.SolicitacaoDAO;
import br.com.fiap.guincho.infra.mensageiro.MensageiroImpl;
import br.com.fiap.guincho.infra.viacep.CorreioImpl;
import br.com.fiap.guincho.service.SolicitacaoService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private RepositorioSolicitacoes solicitacaoDao;
    private SolicitacaoService solicitacaoService;

    public SolicitacaoController() {
        try {
            Mensageiro mensageiro = new MensageiroImpl();
            Correio correios = new CorreioImpl();
            solicitacaoDao = new SolicitacaoDAO();
            File relatorio = new File("relatorio.txt");
            GeradorDeRelatorio gerador = new RelacaoDeChamadosEmArquivo(relatorio);
            solicitacaoService = new SolicitacaoService(mensageiro, correios, solicitacaoDao, gerador);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public ResponseEntity<ArrayList<Solicitacao>> obterTodasSolicitacoes() {
        return ResponseEntity.ok(solicitacaoService.obterTodasSolicitacoes());
    }
}
