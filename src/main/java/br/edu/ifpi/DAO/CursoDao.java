package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.Entidades.Cursos;

public class CursoDao {
    private Connection connection;

    public CursoDao() {
        String supabaseUrl = "jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!";
        try {
            this.connection = DriverManager.getConnection(supabaseUrl);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao Supabase: " + e.getMessage());
        }
    }

    public void cadastrarCurso(String nome, String status, int cargaHoraria) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO cursos (nome, status, carga_horaria) VALUES (?, ?, ?)"
            );
            statement.setString(1, nome);
            statement.setString(2, status);
            statement.setInt(3, cargaHoraria);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    public void atualizarCurso(String nome, String novoStatus) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE cursos SET status = ? WHERE nome = ?"
            );
            statement.setString(1, novoStatus);
            statement.setString(2, nome);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public List<Cursos> listarCursosDisponiveis() {
        List<Cursos> cursos = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM cursos");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String status = resultSet.getString("status");
                int cargaHoraria = resultSet.getInt("carga_horaria");
                Cursos curso = new Cursos(nome, status, cargaHoraria);
                cursos.add(curso);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar cursos disponíveis: " + e.getMessage());
        }
        return cursos;
    }

    public void deletarCurso(String nome) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM cursos WHERE nome = ?"
            );
            statement.setString(1, nome);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar curso: " + e.getMessage());
        }
    }
}