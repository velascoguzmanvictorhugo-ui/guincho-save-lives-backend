package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
import br.com.fiap.guincho.exception.RecursoNaoEncontradoException;
import br.com.fiap.guincho.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuincheiroService {

    private final RepositorioGuincheiros repositorioGuincheiros;

    public GuincheiroService(RepositorioGuincheiros repositorioGuincheiros) {
        this.repositorioGuincheiros = repositorioGuincheiros;
    }

    public void adicionar(Guincheiro guincheiro) {
        validarGuincheiro(guincheiro);
        repositorioGuincheiros.salvar(guincheiro);
    }

    public List<Guincheiro> obterTodosGuincheiros() {
        return repositorioGuincheiros.obterTodosGuincheiros();
    }

    public Guincheiro buscarPorId(long id) {
        Guincheiro guincheiro = repositorioGuincheiros.buscarPorId(id);
        if (guincheiro == null) {
            throw new RecursoNaoEncontradoException("Guincheiro não encontrado.");
        }
        return guincheiro;
    }

    public List<Guincheiro> buscarPorRegiaoOuCidade(String termo) {
        return repositorioGuincheiros.buscarPorRegiaoOuCidade(termo);
    }

    public Guincheiro atualizar(long id, Guincheiro guincheiro) {
        validarGuincheiro(guincheiro);
        buscarPorId(id);
        repositorioGuincheiros.atualizar(id, guincheiro);
        return buscarPorId(id);
    }

    public void excluir(long id) {
        buscarPorId(id);
        try {
            repositorioGuincheiros.excluir(id);
        } catch (RuntimeException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            String causaMsg = (e.getCause() != null && e.getCause().getMessage() != null)
                    ? e.getCause().getMessage() : "";
            if (msg.contains("2292") || causaMsg.contains("2292")
                    || msg.toLowerCase().contains("integrity")
                    || causaMsg.toLowerCase().contains("integrity")) {
                throw new RegraDeNegocioException(
                        "Não é possível excluir o guincheiro pois existem solicitações vinculadas a ele.");
            }
            throw e;
        }
    }

    private void validarGuincheiro(Guincheiro guincheiro) {
        if (guincheiro == null || guincheiro.getNome() == null || guincheiro.getNome().isBlank()) {
            throw new RegraDeNegocioException("O nome do guincheiro é obrigatório.");
        }
        if (guincheiro.getPreco() <= 0) {
            throw new RegraDeNegocioException("A taxa base do guincheiro deve ser maior que zero.");
        }
    }
}