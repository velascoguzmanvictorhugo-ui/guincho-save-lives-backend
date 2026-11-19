package br.com.fiap.guincho.dominio;

import java.util.List;

public interface RepositorioGuincheiros {
    void salvar(Guincheiro guincheiro);
    List<Guincheiro> obterTodosGuincheiros();
    Guincheiro buscarPorId(long id);
    List<Guincheiro> buscarPorRegiaoOuCidade(String termo);
    void atualizar(long id, Guincheiro guincheiro);
    void excluir(long id);
}