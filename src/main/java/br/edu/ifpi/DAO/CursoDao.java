package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.Entidades.Cursos;

public class CursoDao {
    private Connection connection;

    public CursoDao(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarCurso(String nome, String status, int cargaHoraria) {
        String sql = "INSERT INTO cursos (nome, status, carga_horaria) VALUES (?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, status);
            statement.setInt(3, cargaHoraria);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    public void atualizarCurso(String nome, String novoStatus) {
        String sql = "UPDATE cursos SET status = ? WHERE nome = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, novoStatus);
            statement.setString(2, nome);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public List<Cursos> listarCursosDisponiveis() {
        List<Cursos> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos";
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String status = resultSet.getString("status");
                int cargaHoraria = resultSet.getInt("carga_horaria");
                Cursos curso = new Cursos(nome, status, cargaHoraria);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cursos disponíveis: " + e.getMessage());
        }
        return cursos;
    }

    public void deletarCurso(String nome) {
        String sql = "DELETE FROM cursos WHERE nome = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar curso: " + e.getMessage());
        }
    }

    private Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("A conexão com o banco de dados não foi inicializada corretamente.");
        }
        return connection;
    }
}
