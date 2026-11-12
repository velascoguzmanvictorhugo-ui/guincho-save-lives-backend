package br.com.fiap.guincho.dominio;

public class CalculadoraDeTarifas {
    public double calcularTarifaLiquida(ChamadoVeiculo chamado) {
        return chamado.lerValorLiquido() - descontoSindical();
    }

    private double descontoSindical() {
        return 5;
    }
}
