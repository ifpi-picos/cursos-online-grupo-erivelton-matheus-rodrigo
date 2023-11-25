package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ifpi.Entidades.Professor;

public class ProfessorDao {
    private final String supabaseUrl = "jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!";

    public List<Professor> listarProfessores() {
        List<Professor> professores = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(supabaseUrl)) {
            String sql = "SELECT id, nome, email FROM professores";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");

                Professor professor = new Professor(nome, id, email);
                professores.add(professor);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professores;
    }

    public void inserirProfessor(Professor professor) {
        try (Connection connection = DriverManager.getConnection(supabaseUrl)) {
            String sql = "INSERT INTO professores (nome, email) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Verifica se nome e email não estão nulos antes de inserir
            if (professor.getNome() != null && professor.getEmail() != null) {
                statement.setString(1, professor.getNome());
                statement.setString(2, professor.getEmail());

                statement.executeUpdate();
            } else {
                System.out.println("Nome ou email do professor são nulos. Não é possível inserir no banco.");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public void atualizarProfessor(Professor professor) {
        try (Connection connection = DriverManager.getConnection(supabaseUrl)) {
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
        try (Connection connection = DriverManager.getConnection(supabaseUrl)) {
            String sql = "DELETE FROM professores WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}