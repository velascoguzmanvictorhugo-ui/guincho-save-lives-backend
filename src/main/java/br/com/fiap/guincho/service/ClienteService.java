package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Cliente;
import br.com.fiap.guincho.dominio.RepositorioClientes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private RepositorioClientes repositorioClientes;

    public ClienteService(RepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
    }

    public void adicionar(Cliente cliente) {
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new RuntimeException("Regra de Negócio: O e-mail do cliente é obrigatório.");
        }
        repositorioClientes.salvar(cliente);
    }

    public List<Cliente> obterTodos() {
        return repositorioClientes.obterTodos();
    }

    public Cliente obterPorId(Long id) {
        Cliente cliente = repositorioClientes.obterPorId(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado.");
        }
        return cliente;
    }

    public void atualizar(Long id, Cliente cliente) {
        Cliente existente = obterPorId(id); // Valida si existe
        cliente.setId(existente.getId());
        repositorioClientes.atualizar(cliente);
    }

    public void excluir(Long id) {
        obterPorId(id); // Valida si existe antes de excluir
        repositorioClientes.excluir(id);
    }
}
