package br.com.fiap.guincho.dominio;

import br.com.fiap.guincho.infra.arquivo.RelacaoDeChamadosEmArquivo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColecoesERelatorioTest {

    @Test
    void testeDeUsoDeListEArrayList() {
        Guincheiro alex = new Guincheiro("1", "Alex Martins", "Centro", "São Paulo", "Plataforma", 180.0);
        Guincheiro camila = new Guincheiro("2", "Camila Rocha", "Zona Sul", "São Paulo", "Plataforma", 220.0);
        Guincheiro beatriz = new Guincheiro("3", "Beatriz Lima", "Centro", "Campinas", "Plataforma", 195.0);

        List<Guincheiro> guincheiros = new ArrayList<>();
        guincheiros.add(camila);
        guincheiros.add(alex);
        guincheiros.add(beatriz);

        assertEquals(3, guincheiros.size());
        assertEquals(camila, guincheiros.get(0));
        assertTrue(guincheiros.contains(camila));

        guincheiros.remove(camila);
        assertFalse(guincheiros.contains(camila));
        guincheiros.add(camila);
        assertFalse(guincheiros.isEmpty());

        List<Guincheiro> subLista = guincheiros.subList(0, 2);
        assertEquals(2, subLista.size());
    }

    @Test
    void testeDeUsoDeListELinkedList() {
        Guincheiro alex = new Guincheiro("1", "Alex Martins", "Centro", "São Paulo", "Plataforma", 180.0);
        Guincheiro camila = new Guincheiro("2", "Camila Rocha", "Zona Sul", "São Paulo", "Plataforma", 220.0);

        List<Guincheiro> guincheiros = new LinkedList<>();
        guincheiros.add(alex);
        guincheiros.add(camila);

        assertEquals(2, guincheiros.size());
    }

    @Test
    void testeDeUsoDeHashSetETreeSet() {
        Guincheiro camila = new Guincheiro("2", "Camila Rocha", "Zona Sul", "São Paulo", "Plataforma", 220.0);
        Guincheiro alex = new Guincheiro("1", "Alex Martins", "Centro", "São Paulo", "Plataforma", 180.0);

        Set<Guincheiro> conjunto = new HashSet<>();
        conjunto.add(camila);
        conjunto.add(alex);
        conjunto.add(camila);
        assertEquals(2, conjunto.size());

        Set<Guincheiro> ordenados = new TreeSet<>();
        ordenados.add(camila);
        ordenados.add(alex);
        assertEquals(2, ordenados.size());
    }

    @Test
    void lerEEscreverChamados() throws IOException {
        File relatorio = new File("src/test/resources/relatorio.txt");
        if (relatorio.getParentFile() != null) {
            relatorio.getParentFile().mkdirs();
        }
        relatorio.delete();
        RelacaoDeChamadosEmArquivo relacao = new RelacaoDeChamadosEmArquivo(relatorio);

        Solicitacao s1 = new Solicitacao("1", "Fernanda Santos", "Rua A", "Rua B", "Carro", "Pane", "01001000");
        Solicitacao s2 = new Solicitacao("2", "Gustavo Ferreira", "Rua C", "Rua D", "Moto", "Pneu", "01310100");

        relacao.adicionar(s1);
        relacao.adicionar(s2);

        List<String> todos = relacao.listarTodos();
        List<String> esperados = new ArrayList<>();
        esperados.add("Fernanda Santos");
        esperados.add("Gustavo Ferreira");

        assertTrue(todos.containsAll(esperados));
    }
}
