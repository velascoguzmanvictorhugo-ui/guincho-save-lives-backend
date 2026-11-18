package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
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

    // Nuevo método añadido para conectar el controlador con el DAO de búsqueda
    public ArrayList<Guincheiro> buscarPorRegiaoOuCidade(String termo) {
        // Verificamos si el repositorio implementa el método específico del DAO
        if (repositorioGuincheiros instanceof br.com.fiap.guincho.infra.dao.GuincheiroDAO) {
            return ((br.com.fiap.guincho.infra.dao.GuincheiroDAO) repositorioGuincheiros).buscarPorRegiaoOuCidade(termo);
        }
        return repositorioGuincheiros.obterTodosGuincheiros();
    }
}