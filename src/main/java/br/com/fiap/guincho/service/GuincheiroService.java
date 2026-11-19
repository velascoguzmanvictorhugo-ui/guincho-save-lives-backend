package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
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
        return repositorioGuincheiros.buscarPorId(id);
    }

    public List<Guincheiro> buscarPorRegiaoOuCidade(String termo) {
        return repositorioGuincheiros.buscarPorRegiaoOuCidade(termo);
    }

    public Guincheiro atualizar(long id, Guincheiro guincheiro) {
        validarGuincheiro(guincheiro);
        Guincheiro existente = repositorioGuincheiros.buscarPorId(id);
        if (existente == null) {
            return null;
        }
        repositorioGuincheiros.atualizar(id, guincheiro);
        return repositorioGuincheiros.buscarPorId(id);
    }

    public boolean excluir(long id) {
        Guincheiro existente = repositorioGuincheiros.buscarPorId(id);
        if (existente == null) {
            return false;
        }
        repositorioGuincheiros.excluir(id);
        return true;
    }

    private void validarGuincheiro(Guincheiro guincheiro) {
        if (guincheiro == null || guincheiro.getNome() == null || guincheiro.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do guincheiro é obrigatório.");
        }
        if (guincheiro.getPreco() <= 0) {
            throw new IllegalArgumentException("A taxa base do guincheiro deve ser maior que zero.");
        }
    }
}