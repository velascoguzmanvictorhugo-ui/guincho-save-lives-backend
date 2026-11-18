package br.com.fiap.guincho.infra.dao;

import br.com.fiap.guincho.dominio.RepositorioSolicitacoes;
import br.com.fiap.guincho.dominio.Solicitacao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SolicitacaoDAO implements RepositorioSolicitacoes {

    private long obterProximoIdSolicitacoes() {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            // Atualizado para a sequência correta do script SQL
            String sql = "SELECT tb_solicitacao_seq.NEXTVAL FROM DUAL";
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
            // Atualizado para a tabela tb_solicitacao e id_solicitacao
            String sql = "INSERT INTO tb_solicitacao (id_solicitacao, nome_cliente, origem, destino, veiculo, problema, cep, cliente_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql);
            comandoDeInsercao.setLong(1, obterProximoIdSolicitacoes());
            comandoDeInsercao.setString(2, solicitacao.getNomeCliente());
            comandoDeInsercao.setString(3, solicitacao.getOrigem());
            comandoDeInsercao.setString(4, solicitacao.getDestino());
            comandoDeInsercao.setString(5, solicitacao.getVeiculo());
            comandoDeInsercao.setString(6, solicitacao.getProblema());
            comandoDeInsercao.setString(7, solicitacao.getCep());

            // Grava a Foreign Key (trata caso venha nulo)
            if (solicitacao.getClienteId() != null) {
                comandoDeInsercao.setLong(8, solicitacao.getClienteId());
            } else {
                comandoDeInsercao.setNull(8, java.sql.Types.NUMERIC);
            }

            comandoDeInsercao.execute();
            comandoDeInsercao.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Solicitacao> obterTodasSolicitacoes() {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            // Atualizado para buscar da tb_solicitacao com id_solicitacao
            String sql = "SELECT id_solicitacao, cliente_id, nome_cliente, origem, destino, veiculo, problema, cep FROM tb_solicitacao";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql);
            ResultSet rs = comandoDeSelecao.executeQuery();
            while (rs.next()) {
                // Recupera o Long, garantindo que o Java não o transforme em 0 se for nulo
                Long clienteId = (Long) rs.getObject("cliente_id");

                Solicitacao s = new Solicitacao(
                        String.valueOf(rs.getLong("id_solicitacao")),
                        clienteId,
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