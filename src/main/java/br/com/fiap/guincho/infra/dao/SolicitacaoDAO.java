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

    private long obterProximoIdSolicitacoes(Connection conexao) throws SQLException {
        String sql = "SELECT tb_solicitacao_seq.NEXTVAL FROM DUAL";
        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {
            return rs.next() ? rs.getLong(1) : 1L;
        }
    }

    @Override
    public void salvar(Solicitacao solicitacao) {
        String sql = "INSERT INTO tb_solicitacao (id_solicitacao, nome_cliente, origem, destino, veiculo, problema, cep, cliente_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {

            long proximoId = obterProximoIdSolicitacoes(conexao);
            comandoDeInsercao.setLong(1, proximoId);
            comandoDeInsercao.setString(2, solicitacao.getNomeCliente());
            comandoDeInsercao.setString(3, solicitacao.getOrigem());
            comandoDeInsercao.setString(4, solicitacao.getDestino());
            comandoDeInsercao.setString(5, solicitacao.getVeiculo());
            comandoDeInsercao.setString(6, solicitacao.getProblema());
            comandoDeInsercao.setString(7, solicitacao.getCep());

            if (solicitacao.getClienteId() != null) {
                comandoDeInsercao.setLong(8, solicitacao.getClienteId());
            } else {
                comandoDeInsercao.setNull(8, java.sql.Types.NUMERIC);
            }

            comandoDeInsercao.execute();
            solicitacao.setId(String.valueOf(proximoId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Solicitacao> obterTodasSolicitacoes() {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        String sql = "SELECT id_solicitacao, cliente_id, nome_cliente, origem, destino, veiculo, problema, cep FROM tb_solicitacao ORDER BY id_solicitacao DESC";
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql);
             ResultSet rs = comandoDeSelecao.executeQuery()) {

            while (rs.next()) {
                long clienteIdRaw = rs.getLong("cliente_id");
                Long clienteId = rs.wasNull() ? null : clienteIdRaw;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return solicitacoes;
    }
}