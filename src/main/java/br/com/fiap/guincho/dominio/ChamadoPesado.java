package br.com.fiap.guincho.dominio;

public class ChamadoPesado extends ChamadoVeiculo {
    public ChamadoPesado(double valorBruto) {
        super(valorBruto);
    }

    @Override
    public double lerValorLiquido() {
        return super.lerValorBruto() * 0.9;
    }
}
