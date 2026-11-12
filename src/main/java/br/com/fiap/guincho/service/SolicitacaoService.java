package br.com.fiap.guincho.service;

import br.com.fiap.guincho.dominio.Correio;
import br.com.fiap.guincho.dominio.Endereco;
import br.com.fiap.guincho.dominio.GeradorDeRelatorio;
import br.com.fiap.guincho.dominio.Mensageiro;
import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class SolicitacaoService {

    private Mensageiro mensageiro;
    private Correio correios;
    private RepositorioSolicitacoes repositorioSolicitacoes;
    private GeradorDeRelatorio geradorDeRelatorio;

    public SolicitacaoService(Mensageiro mensageiro, Correio correios,
                              RepositorioSolicitacoes repositorioSolicitacoes,
                              GeradorDeRelatorio geradorDeRelatorio) {
        this.mensageiro = mensageiro;
        this.correios = correios;
        this.repositorioSolicitacoes = repositorioSolicitacoes;
        this.geradorDeRelatorio = geradorDeRelatorio;
    }

    public void adicionar(Solicitacao solicitacao) {
        if (solicitacao.getCep() != null) {
            Endereco endereco = correios.buscarEnderecoPor(solicitacao.getCep());
            solicitacao.atualizarEndereco(endereco);
        }
        mensageiro.enviarMensagemDeBoasVindasPara(solicitacao);
        repositorioSolicitacoes.salvar(solicitacao);
        try {
            geradorDeRelatorio.adicionar(solicitacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Solicitacao> obterTodasSolicitacoes() {
        return repositorioSolicitacoes.obterTodasSolicitacoes();
    }

    public Map<String, Integer> contadorDePalavras(String texto) {
        Map<String, Integer> palavras = new TreeMap<>();
        for (String palavra : Arrays.asList(texto.split(" "))) {
            if (palavras.containsKey(palavra)) {
                palavras.put(palavra, palavras.get(palavra) + 1);
            } else {
                palavras.put(palavra, 1);
            }
        }
        return palavras;
    }
}
