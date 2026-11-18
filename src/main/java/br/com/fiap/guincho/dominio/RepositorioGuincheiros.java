package br.com.fiap.guincho.dominio;

import java.util.ArrayList;

public interface RepositorioGuincheiros {
    void salvar(Guincheiro guincheiro);
    ArrayList<Guincheiro> obterTodosGuincheiros();
    ArrayList<Guincheiro> buscarPorRegiaoOuCidade(String termo);
}
