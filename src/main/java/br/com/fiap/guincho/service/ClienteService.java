package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Cliente;
import br.com.fiap.guincho.dominio.RepositorioClientes;
import br.com.fiap.guincho.exception.RecursoNaoEncontradoException;
import br.com.fiap.guincho.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final RepositorioClientes repositorioClientes;

    public ClienteService(RepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
    }

    public void adicionar(Cliente cliente) {
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new RegraDeNegocioException("O e-mail do cliente é obrigatório.");
        }
        repositorioClientes.salvar(cliente);
    }

    public List<Cliente> obterTodos() {
        return repositorioClientes.obterTodos();
    }

    public Cliente obterPorId(Long id) {
        Cliente cliente = repositorioClientes.obterPorId(id);
        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }
        return cliente;
    }

    public void atualizar(Long id, Cliente cliente) {
        Cliente existente = obterPorId(id);
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new RegraDeNegocioException("O e-mail do cliente é obrigatório.");
        }
        cliente.setId(existente.getId());
        repositorioClientes.atualizar(cliente);
    }

    public void excluir(Long id) {
        obterPorId(id);
        try {
            repositorioClientes.excluir(id);
        } catch (RuntimeException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            String causaMsg = (e.getCause() != null && e.getCause().getMessage() != null)
                    ? e.getCause().getMessage() : "";
            if (msg.contains("2292") || causaMsg.contains("2292")
                    || msg.toLowerCase().contains("integrity")
                    || causaMsg.toLowerCase().contains("integrity")) {
                throw new RegraDeNegocioException(
                        "Não é possível excluir o cliente pois existem solicitações vinculadas a ele.");
            }
            throw e;
        }
    }
}