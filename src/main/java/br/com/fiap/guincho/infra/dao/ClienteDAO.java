package br.com.fiap.guincho.infra.dao;

import br.com.fiap.guincho.dominio.Cliente;
import br.com.fiap.guincho.dominio.RepositorioClientes;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteDAO implements RepositorioClientes {

    private long obterProximoId() {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT SQ_GSL_CLIENTE.NEXTVAL FROM DUAL";
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
    public void salvar(Cliente cliente) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "INSERT INTO T_GSL_CLIENTE (id, nome, email, telefone) VALUES (?, ?, ?, ?)";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, obterProximoId());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getEmail());
            comando.setString(4, cliente.getTelefone());
            comando.execute();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> obterTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id, nome, email, telefone FROM T_GSL_CLIENTE";
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                ));
            }
            rs.close();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    @Override
    public Cliente obterPorId(Long id) {
        Cliente cliente = null;
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "SELECT id, nome, email, telefone FROM T_GSL_CLIENTE WHERE id = ?";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, id);
            ResultSet rs = comando.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
            }
            rs.close();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    @Override
    public void atualizar(Cliente cliente) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "UPDATE T_GSL_CLIENTE SET nome = ?, email = ?, telefone = ? WHERE id = ?";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getEmail());
            comando.setString(3, cliente.getTelefone());
            comando.setLong(4, cliente.getId());
            comando.execute();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(Long id) {
        try {
            Connection conexao = new ConnectionFactory().getConnection();
            String sql = "DELETE FROM T_GSL_CLIENTE WHERE id = ?";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, id);
            comando.execute();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}