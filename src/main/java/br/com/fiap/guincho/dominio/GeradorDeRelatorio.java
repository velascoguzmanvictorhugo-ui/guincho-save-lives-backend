package br.com.fiap.guincho.dominio;

import java.io.IOException;
import java.util.List;

public interface GeradorDeRelatorio {
    void adicionar(Solicitacao solicitacao) throws IOException;
    List<String> listarTodos() throws IOException;
}
