package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    private RepositorioSolicitacoes repositorioSolicitacoes;

    public SolicitacaoService(RepositorioSolicitacoes repositorioSolicitacoes) {
        this.repositorioSolicitacoes = repositorioSolicitacoes;
    }

    public void adicionar(Solicitacao solicitacao) {
        repositorioSolicitacoes.salvar(solicitacao);
    }

    public List<Solicitacao> obterTodasSolicitacoes() { // Cambiado a List
        return repositorioSolicitacoes.obterTodasSolicitacoes();
    }
}
