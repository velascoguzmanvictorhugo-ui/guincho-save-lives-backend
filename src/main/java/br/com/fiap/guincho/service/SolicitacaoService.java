package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Correio;
import br.com.fiap.guincho.dominio.Endereco;
import br.com.fiap.guincho.dominio.GeradorDeRelatorio;
import br.com.fiap.guincho.dominio.Mensageiro;
import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class SolicitacaoService {
    private final Mensageiro mensageiro;
    private final Correio correios;
    private final RepositorioSolicitacoes repositorioSolicitacoes;
    private final GeradorDeRelatorio geradorDeRelatorio;

    public SolicitacaoService(Mensageiro mensageiro, Correio correios,
                              RepositorioSolicitacoes repositorioSolicitacoes,
                              GeradorDeRelatorio geradorDeRelatorio) {
        this.mensageiro = mensageiro;
        this.correios = correios;
        this.repositorioSolicitacoes = repositorioSolicitacoes;
        this.geradorDeRelatorio = geradorDeRelatorio;
    }

    public void adicionar(Solicitacao solicitacao) {
        if (solicitacao.getCep() != null) {
            Endereco endereco = correios.buscarEnderecoPor(solicitacao.getCep());
            solicitacao.atualizarEndereco(endereco);
        }
        mensageiro.enviarMensagemDeBoasVindasPara(solicitacao);
        repositorioSolicitacoes.salvar(solicitacao);
        try {
            geradorDeRelatorio.adicionar(solicitacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Solicitacao> obterTodasSolicitacoes() {
        return repositorioSolicitacoes.obterTodasSolicitacoes();
    }
}

