package br.com.fiap.guincho.dominio;

import java.util.List;

public interface RepositorioSolicitacoes {
    void salvar(Solicitacao solicitacao);
    List<Solicitacao> obterTodasSolicitacoes(); // Cambiado a List
}