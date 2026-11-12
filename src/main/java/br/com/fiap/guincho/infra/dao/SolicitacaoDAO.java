package br.com.fiap.guincho.infra.dao;

import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SolicitacaoDAO implements RepositorioSolicitacoes {

    private long obterProximoIdSolicitacoes() {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT SQ_GSL_SOLICITACAO.NEXTVAL FROM DUAL";
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();
            long id = 1;
            if (rs.next()) {
                id = rs.getLong(1);
            }
            rs.close();
            comando.close();
            conexao.close();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void salvar(Solicitacao solicitacao) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "INSERT INTO T_GSL_SOLICITACAO (id, nome_cliente, origem, destino, veiculo, problema, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql);
            comandoDeInsercao.setLong(1, obterProximoIdSolicitacoes());
            comandoDeInsercao.setString(2, solicitacao.getNomeCliente());
            comandoDeInsercao.setString(3, solicitacao.getOrigem());
            comandoDeInsercao.setString(4, solicitacao.getDestino());
            comandoDeInsercao.setString(5, solicitacao.getVeiculo());
            comandoDeInsercao.setString(6, solicitacao.getProblema());
            comandoDeInsercao.setString(7, solicitacao.getCep());
            comandoDeInsercao.execute();
            comandoDeInsercao.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Solicitacao> obterTodasSolicitacoes() {
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id, nome_cliente, origem, destino, veiculo, problema, cep FROM T_GSL_SOLICITACAO";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql);
            ResultSet rs = comandoDeSelecao.executeQuery();
            while (rs.next()) {
                Solicitacao s = new Solicitacao(
                        String.valueOf(rs.getLong("id")),
                        rs.getString("nome_cliente"),
                        rs.getString("origem"),
                        rs.getString("destino"),
                        rs.getString("veiculo"),
                        rs.getString("problema"),
                        rs.getString("cep")
                );
                solicitacoes.add(s);
            }
            rs.close();
            comandoDeSelecao.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return solicitacoes;
    }
}
