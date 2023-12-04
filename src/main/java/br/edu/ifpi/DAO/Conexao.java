package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    public static void main(String[] args) {
        String supabaseUrl = "jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!";

        try {
            Connection connection = DriverManager.getConnection(supabaseUrl);

            if (connection != null) {
                System.out.println("Conexão com Supabase estabelecida com sucesso!");

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT version()");

                if (resultSet.next()) {
                    String version = resultSet.getString(1);
                    System.out.println("Versão do PostgreSQL: " + version);
                }

                resultSet.close();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao Supabase: " + e.getMessage());
        }
    }

    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!");
        return connection.prepareStatement(sql);
    }
}