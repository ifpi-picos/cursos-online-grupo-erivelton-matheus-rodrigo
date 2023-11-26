package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.Entidades.Professor;

public class ProfessorDao {
    private Connection connection;

    public ProfessorDao(Connection connection) {
        this.connection = connection;
    }

    public void inserirProfessor(Professor professor) {
        try {
            if (connection != null) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO professores (nome, email) VALUES (?, ?)");
                stmt.setString(1, professor.getNome());
                stmt.setString(2, professor.getEmail());
                stmt.executeUpdate();
                stmt.close();
            } else {
                System.out.println("A conexão com o banco de dados é nula.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarProfessor(Professor professor) {
        try {
            String sql = "UPDATE professores SET nome = ?, email = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, professor.getNome());
            statement.setString(2, professor.getEmail());
            statement.setInt(3, professor.getId());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarProfessor(int id) {
        try {
            String sql = "DELETE FROM professores WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Professor> listarProfessores() {
        List<Professor> professores = new ArrayList<>();

        try {
            String sql = "SELECT * FROM professores";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idProfessor = resultSet.getInt("id");
                String nomeProfessor = resultSet.getString("nome");
                String emailProfessor = resultSet.getString("email");

                Professor professor = new Professor(nomeProfessor, idProfessor, emailProfessor);
                professores.add(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professores;
    }
}
