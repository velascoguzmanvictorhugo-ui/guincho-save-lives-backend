package br.com.fiap.guincho.dominio;

import java.util.List;

public interface RepositorioClientes {
    void salvar(Cliente cliente);
    List<Cliente> obterTodos();
    Cliente obterPorId(Long id);
    void atualizar(Cliente cliente);
    void excluir(Long id);
}