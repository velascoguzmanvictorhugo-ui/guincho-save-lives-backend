package br.com.fiap.guincho.infra.dao;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuincheiroDAO implements RepositorioGuincheiros {

    private long obterProximoIdGuincheiros() {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT tb_guincheiro_seq.NEXTVAL FROM DUAL";
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
    public void salvar(Guincheiro guincheiro) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "INSERT INTO tb_guincheiro (id_guincheiro, nome, regiao, cidade, tipo_veiculo, taxa_base) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql);
            comandoDeInsercao.setLong(1, obterProximoIdGuincheiros());
            comandoDeInsercao.setString(2, guincheiro.getNome());
            comandoDeInsercao.setString(3, guincheiro.getRegiao());
            comandoDeInsercao.setString(4, guincheiro.getCidade());
            comandoDeInsercao.setString(5, guincheiro.getTipoGuincho());
            comandoDeInsercao.setDouble(6, guincheiro.getPreco());
            comandoDeInsercao.execute();
            comandoDeInsercao.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Guincheiro> obterTodosGuincheiros() {
        List<Guincheiro> guincheiros = new ArrayList<>();
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id_guincheiro, nome, regiao, cidade, tipo_veiculo, taxa_base FROM tb_guincheiro";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql);
            ResultSet rs = comandoDeSelecao.executeQuery();
            while (rs.next()) {
                Guincheiro g = new Guincheiro(
                        String.valueOf(rs.getLong("id_guincheiro")),
                        rs.getString("nome"),
                        rs.getString("regiao"),
                        rs.getString("cidade"),
                        rs.getString("tipo_veiculo"),
                        rs.getDouble("taxa_base")
                );
                guincheiros.add(g);
            }
            rs.close();
            comandoDeSelecao.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return guincheiros;
    }

    @Override
    public Guincheiro buscarPorId(long id) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id_guincheiro, nome, regiao, cidade, tipo_veiculo, taxa_base FROM tb_guincheiro WHERE id_guincheiro = ?";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, id);
            ResultSet rs = comando.executeQuery();
            Guincheiro guincheiro = null;
            if (rs.next()) {
                guincheiro = new Guincheiro(
                        String.valueOf(rs.getLong("id_guincheiro")),
                        rs.getString("nome"),
                        rs.getString("regiao"),
                        rs.getString("cidade"),
                        rs.getString("tipo_veiculo"),
                        rs.getDouble("taxa_base")
                );
            }
            rs.close();
            comando.close();
            conexao.close();
            return guincheiro;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Guincheiro> buscarPorRegiaoOuCidade(String termo) {
        List<Guincheiro> guincheiros = new ArrayList<>();
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id_guincheiro, nome, regiao, cidade, tipo_veiculo, taxa_base " +
                    "FROM tb_guincheiro " +
                    "WHERE LOWER(regiao) LIKE LOWER(?) OR LOWER(cidade) LIKE LOWER(?)";
            PreparedStatement comando = conexao.prepareStatement(sql);
            String parametro = "%" + termo + "%";
            comando.setString(1, parametro);
            comando.setString(2, parametro);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Guincheiro g = new Guincheiro(
                        String.valueOf(rs.getLong("id_guincheiro")),
                        rs.getString("nome"),
                        rs.getString("regiao"),
                        rs.getString("cidade"),
                        rs.getString("tipo_veiculo"),
                        rs.getDouble("taxa_base")
                );
                guincheiros.add(g);
            }
            rs.close();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return guincheiros;
    }

    @Override
    public void atualizar(long id, Guincheiro guincheiro) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "UPDATE tb_guincheiro SET nome = ?, regiao = ?, cidade = ?, tipo_veiculo = ?, taxa_base = ? WHERE id_guincheiro = ?";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, guincheiro.getNome());
            comando.setString(2, guincheiro.getRegiao());
            comando.setString(3, guincheiro.getCidade());
            comando.setString(4, guincheiro.getTipoGuincho());
            comando.setDouble(5, guincheiro.getPreco());
            comando.setLong(6, id);
            comando.executeUpdate();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(long id) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "DELETE FROM tb_guincheiro WHERE id_guincheiro = ?";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, id);
            comando.executeUpdate();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}