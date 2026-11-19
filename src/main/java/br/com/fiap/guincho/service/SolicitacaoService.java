package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import br.com.fiap.guincho.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    private final RepositorioSolicitacoes repositorioSolicitacoes;

    public SolicitacaoService(RepositorioSolicitacoes repositorioSolicitacoes) {
        this.repositorioSolicitacoes = repositorioSolicitacoes;
    }

    public void adicionar(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new RegraDeNegocioException("Os dados da solicitação são obrigatórios.");
        }
        try {
            repositorioSolicitacoes.salvar(solicitacao);
        } catch (RuntimeException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            String causaMsg = (e.getCause() != null && e.getCause().getMessage() != null)
                    ? e.getCause().getMessage() : "";
            if (msg.contains("2291") || causaMsg.contains("2291")
                    || msg.toLowerCase().contains("integrity")
                    || causaMsg.toLowerCase().contains("integrity")) {
                throw new RegraDeNegocioException(
                        "Não foi possível registrar a solicitação: cliente ou guincheiro informado não existe.");
            }
            throw e;
        }
    }

    public List<Solicitacao> obterTodasSolicitacoes() {
        return repositorioSolicitacoes.obterTodasSolicitacoes();
    }
}