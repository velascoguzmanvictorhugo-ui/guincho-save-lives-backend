package br.com.fiap.guincho.dominio;

public class ChamadoVeiculo {
    private double valorBruto;

    public ChamadoVeiculo(double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public double lerValorBruto() {
        return valorBruto;
    }

    public double lerValorLiquido() {
        return valorBruto * 0.7;
    }
}
