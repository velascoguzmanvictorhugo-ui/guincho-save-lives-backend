package br.com.fiap.guincho.dominio;

public class CalculadoraDeTarifas {
    public double calcularTarifaLiquida(ChamadoVeiculo chamado) {
        return chamado.lerValorLiquido() - taxaPlataforma();
    }

    private double taxaPlataforma() {
        return 10.0;
    }
}
