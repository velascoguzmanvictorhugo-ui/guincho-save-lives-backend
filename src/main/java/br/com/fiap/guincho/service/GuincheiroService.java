package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
import java.util.ArrayList;

public class GuincheiroService {

    private RepositorioGuincheiros repositorioGuincheiros;

    public GuincheiroService(RepositorioGuincheiros repositorioGuincheiros) {
        this.repositorioGuincheiros = repositorioGuincheiros;
    }

    public void adicionar(Guincheiro guincheiro) {
        repositorioGuincheiros.salvar(guincheiro);
    }

    public ArrayList<Guincheiro> obterTodosGuincheiros() {
        return repositorioGuincheiros.obterTodosGuincheiros();
    }
}
