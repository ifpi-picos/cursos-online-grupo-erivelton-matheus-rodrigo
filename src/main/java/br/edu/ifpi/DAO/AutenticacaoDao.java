package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.ifpi.Entidades.Alunos;

public class AutenticacaoDao {
    private Connection connection;

    public AutenticacaoDao(Connection connection) {
        this.connection = connection;
    }

    public Alunos autenticarAluno(String email, int id) throws SQLException {
        String sql = "SELECT id, nome, email FROM alunos WHERE email = ? AND id = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            stm.setInt(2, id);
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                    int alunoId = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");

                    System.out.println("Aluno autenticado com sucesso!");
                    return new Alunos(nome, alunoId, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao autenticar aluno: " + e.getMessage());
        }
        return null;
    }
}