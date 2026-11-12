package br.com.fiap.guincho.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Solicitacao {

    @JsonProperty("id")
    private String id;

    @JsonProperty("clientName")
    private String nomeCliente;

    @JsonProperty("origin")
    private String origem;

    @JsonProperty("destination")
    private String destino;

    @JsonProperty("vehicle")
    private String veiculo;

    @JsonProperty("problem")
    private String problema;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("endereco")
    private Endereco endereco;

    public Solicitacao() {
    }

    public Solicitacao(String id, String nomeCliente, String origem, String destino, String veiculo, String problema, String cep) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.origem = origem;
        this.destino = destino;
        this.veiculo = veiculo;
        this.problema = problema;
        this.cep = cep;
    }

    public void atualizarEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public String getVeiculo() { return veiculo; }
    public String getProblema() { return problema; }
    public String getCep() { return cep; }
    public Endereco getEndereco() { return endereco; }
}
