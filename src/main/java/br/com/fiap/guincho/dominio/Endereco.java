package br.com.fiap.guincho.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Endereco {
    @JsonProperty
    private String logradouro;
    @JsonProperty
    private String bairro;
    @JsonProperty
    private String complemento;
    @JsonProperty
    private String localidade;
    @JsonProperty
    private String uf;
    @JsonProperty
    private String cep;

    public Endereco() {
    }

    public Endereco(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() { return logradouro; }
    public String getBairro() { return bairro; }
    public String getComplemento() { return complemento; }
    public String getLocalidade() { return localidade; }
    public String getUf() { return uf; }
    public String getCep() { return cep; }
}
