package br.com.fiap.guincho.infra.viacep;

import br.com.fiap.guincho.dominio.Correio;
import br.com.fiap.guincho.dominio.Endereco;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CorreioImpl implements Correio {

    @Override
    public Endereco buscarEnderecoPor(String cep) {
        OkHttpClient clienteHttp = new OkHttpClient().newBuilder()
                .build();
        Request requisicao = new Request.Builder()
                .url("https://viacep.com.br/ws/" + cep + "/json")
                .get()
                .build();
        Endereco endereco = null;
        try {
            Response resposta = clienteHttp.newCall(requisicao).execute();
            if (resposta.isSuccessful()) {
                String responseBody = resposta.body().string();
                endereco = new Gson().fromJson(responseBody, Endereco.class);
            } else {
                System.out.println("Erro ao obter o endereço. Código de resposta: " + resposta.code());
            }
            resposta.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return endereco;
    }
}
