package br.com.fiap.guincho.infra.arquivo;

import br.com.fiap.guincho.dominio.GeradorDeRelatorio;
import br.com.fiap.guincho.dominio.Solicitacao;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class RelacaoDeChamadosEmArquivo implements GeradorDeRelatorio {

    private File relatorio;

    public RelacaoDeChamadosEmArquivo(File relatorio) throws IOException {
        this.relatorio = relatorio;
        this.relatorio.createNewFile();
    }

    @Override
    public void adicionar(Solicitacao solicitacao) throws IOException {
        String linha = solicitacao.getNomeCliente() + "\n";
        Files.write(relatorio.toPath(), linha.getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public List<String> listarTodos() throws IOException {
        return Files.readAllLines(relatorio.toPath());
    }
}
