package br.com.fiap.guincho.dominio;

import java.util.ArrayList;

public interface RepositorioSolicitacoes {
    void salvar(Solicitacao solicitacao);
    ArrayList<Solicitacao> obterTodasSolicitacoes();
}
