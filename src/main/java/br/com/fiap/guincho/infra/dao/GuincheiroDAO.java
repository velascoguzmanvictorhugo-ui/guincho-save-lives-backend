package br.com.fiap.guincho.infra.dao;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuincheiroDAO implements RepositorioGuincheiros {

    private long obterProximoIdGuincheiros() {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT SQ_GSL_GUINCHEIRO.NEXTVAL FROM DUAL";
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
            String sql = "INSERT INTO T_GSL_GUINCHEIRO (id, nome, regiao, cidade, tipo_guincho, preco) VALUES (?, ?, ?, ?, ?, ?)";
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
    public ArrayList<Guincheiro> obterTodosGuincheiros() {
        ArrayList<Guincheiro> guincheiros = new ArrayList<>();
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id, nome, regiao, cidade, tipo_guincho, preco FROM T_GSL_GUINCHEIRO";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql);
            ResultSet rs = comandoDeSelecao.executeQuery();
            while (rs.next()) {
                Guincheiro g = new Guincheiro(
                        String.valueOf(rs.getLong("id")),
                        rs.getString("nome"),
                        rs.getString("regiao"),
                        rs.getString("cidade"),
                        rs.getString("tipo_guincho"),
                        rs.getDouble("preco")
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
}
