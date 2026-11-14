package br.com.fiap.guincho.infra.mensageiro;

import br.com.fiap.guincho.dominio.Mensageiro;
import br.com.fiap.guincho.dominio.Solicitacao;
import org.springframework.stereotype.Component;

@Component
public class MensageiroImpl implements Mensageiro {

    @Override
    public void enviarMensagemDeBoasVindasPara(Solicitacao solicitacao) {
        System.out.println("Mensagem enviada para: " + solicitacao.getNomeCliente());
    }
}
