package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpi.Entidades.Cursos;
import br.edu.ifpi.Entidades.Professor;

public class ProfessorDao {
    private final String supabaseUrl = "jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!";

    public ProfessorDao(Connection conexao) {
    }

        public void associarCurso(int idProfessor, int idCurso) {
        try (Connection connection = DriverManager.getConnection(supabaseUrl)) {
            String sql = "INSERT INTO cursos_ministrados (id_professor, id_curso) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idProfessor);
            statement.setInt(2, idCurso);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Professor> listarProfessoresComCursos() {
        List<Professor> professores = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(supabaseUrl)) {
            String sql = "SELECT p.id, p.nome, p.email, c.id AS id_curso, c.nome AS nome_curso " +
                         "FROM professores p " +
                         "LEFT JOIN cursos_ministrados cm ON p.id = cm.id_professor " +
                         "LEFT JOIN cursos c ON cm.id_curso = c.id";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Professor> professorMap = new HashMap<>();

            while (resultSet.next()) {
                int idProfessor = resultSet.getInt("id");
                String nomeProfessor = resultSet.getString("nome");
                String emailProfessor = resultSet.getString("email");

                Professor professor;
                if (!professorMap.containsKey(idProfessor)) {
                    professor = new Professor(nomeProfessor, idProfessor, emailProfessor);
                    professorMap.put(idProfessor, professor);
                    professores.add(professor);
                } else {
                    professor = professorMap.get(idProfessor);
                }

                int idCurso = resultSet.getInt("id_curso");
                String nomeCurso = resultSet.getString("nome_curso");

                if (idCurso != 0 && nomeCurso != null) {
                    Cursos curso = new Cursos(idCurso, nomeCurso, null, 0);
                    professor.adicionarCurso(curso);
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professores;
    }

    public void inserirProfessor(Professor professor, Connection connection) {
        try {
            String sql = "INSERT INTO professores (nome, email) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, professor.getNome());
            statement.setString(2, professor.getEmail());

            statement.executeUpdate();
            statement.close();

            for (Cursos curso : professor.getCursosMinistrados()) {
                cadastrarCursoMinistrado(professor.getId(), curso, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    private void cadastrarCursoMinistrado(int idProfessor, Cursos curso, Connection connection) {
        try {
            String sql = "INSERT INTO cursos_ministrados (id_professor, nome_curso, status, carga_horaria) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, idProfessor);
            statement.setString(2, curso.getNome());
            statement.setString(3, curso.getStatus());
            statement.setInt(4, curso.getCargaHoraria());

            statement.executeUpdate();
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