package br.com.fiap.guincho.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Solicitacao {

    @JsonProperty("id")
    private String id;

    // Llave foránea lógica (Relación 1:N con Cliente)
    @JsonProperty("clienteId")
    private Long clienteId;

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

    public Solicitacao(String id, Long clienteId, String nomeCliente, String origem, String destino, String veiculo, String problema, String cep) {
        this.id = id;
        this.clienteId = clienteId;
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
    public void setId(String id) { this.id = id; }

    // Getter y Setter para la relación 1:N
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }

    public String getProblema() { return problema; }
    public void setProblema(String problema) { this.problema = problema; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Endereco getEndereco() { return endereco; }
}