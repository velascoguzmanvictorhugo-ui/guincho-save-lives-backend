package br.com.fiap.guincho.infra.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection() {
        String urlDeConexao = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String login = "rm573173";
        String senha = "250499";

        try {
            return DriverManager.getConnection(urlDeConexao, login, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
