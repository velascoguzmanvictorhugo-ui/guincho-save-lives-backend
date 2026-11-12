package br.com.fiap.guincho.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Guincheiro implements Comparable<Guincheiro> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("region")
    private String regiao;

    @JsonProperty("city")
    private String cidade;

    @JsonProperty("towType")
    private String tipoGuincho;

    @JsonProperty("price")
    private double preco;

    public Guincheiro() {
    }

    public Guincheiro(String id, String nome, String regiao, String cidade, String tipoGuincho, double preco) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.cidade = cidade;
        this.tipoGuincho = tipoGuincho;
        this.preco = preco;
    }

    @Override
    public int compareTo(Guincheiro o) {
        return nome.compareTo(o.nome);
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getRegiao() { return regiao; }
    public String getCidade() { return cidade; }
    public String getTipoGuincho() { return tipoGuincho; }
    public double getPreco() { return preco; }
}
