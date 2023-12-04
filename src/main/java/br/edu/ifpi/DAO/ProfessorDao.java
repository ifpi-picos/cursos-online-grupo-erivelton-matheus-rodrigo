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

    public int inserirProfessor(Professor professor) throws SQLException {
        int novoId = 0;
        String sql = "INSERT INTO professores (nome, email) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("A inserção falhou, nenhum registro foi criado.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    novoId = generatedKeys.getInt(1);
                    professor.setId(novoId);
                } else {
                    throw new SQLException("A inserção falhou, nenhum ID foi gerado.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir professor: " + e.getMessage());
        }
        return novoId;
    }

    public void atualizarProfessor(Professor professor) throws SQLException {
        String sql = "UPDATE professores SET nome = ?, email = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, professor.getNome());
            statement.setString(2, professor.getEmail());
            statement.setInt(3, professor.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    public void deletarProfessor(int id) throws SQLException {
        String sql = "DELETE FROM professores WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar professor: " + e.getMessage());
        }
    }

    public List<Professor> listarProfessores() throws SQLException {
        List<Professor> professores = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM professores");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idProfessor = resultSet.getInt("id");
                String nomeProfessor = resultSet.getString("nome");
                String emailProfessor = resultSet.getString("email");

                Professor professor = new Professor(nomeProfessor, idProfessor, emailProfessor);
                professores.add(professor);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar professores: " + e.getMessage());
        }
        return professores;
    }

    public void associarProfessorCurso(int idProfessor, int idCurso) throws SQLException {
        String sql = "INSERT INTO professor_curso (id_professor, id_curso) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProfessor);
            stmt.setInt(2, idCurso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao associar professor a curso: " + e.getMessage());
        }
    }

    public int obterNovoId() throws SQLException {
        int novoId = 0;
        String sql = "SELECT MAX(id) FROM professores";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                novoId = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao obter novo ID: " + e.getMessage());
        }
        return novoId;
    }
}
