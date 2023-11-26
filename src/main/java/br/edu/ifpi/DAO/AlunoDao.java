package br.edu.ifpi.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.Entidades.Alunos;

public class AlunoDao {
    private Connection connection;

    public AlunoDao(Connection conexao) {
        String supabaseUrl = "jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!";

        try {
            this.connection = DriverManager.getConnection(supabaseUrl);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao Supabase: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int salvar(Alunos aluno) {
        String sql = "INSERT INTO alunos (nome, email) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            int affectedRows = ps.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("A inserção falhou, nenhum registro foi criado.");
            }
    
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.setId(generatedKeys.getInt(1));
                    return aluno.getId();
                } else {
                    throw new SQLException("A inserção falhou, nenhum ID foi gerado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }    

    public Alunos buscarPorId(int id) {
        Alunos aluno = null;
        String sql = "SELECT * FROM alunos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aluno = new Alunos(rs.getString("nome"), rs.getInt("id"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    public List<Alunos> listarTodos() {
        List<Alunos> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alunos aluno = new Alunos(rs.getString("nome"), rs.getInt("id"), rs.getString("email"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public void atualizar(Alunos aluno) {
        String sql = "UPDATE alunos SET nome = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.setInt(3, aluno.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void associarAlunoCurso(int idAluno, int idCurso) {
        String sql = "INSERT INTO aluno_curso (id_aluno, id_curso) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int obterNovoId() {
        int novoId = 0;
        String sql = "SELECT MAX(id) FROM alunos";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                novoId = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novoId;
    }
}