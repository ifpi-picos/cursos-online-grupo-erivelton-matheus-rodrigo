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

    public Cursos buscarCursoPorNome(String nome) {
        String sql = "SELECT * FROM cursos WHERE nome = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    int cargaHoraria = resultSet.getInt("carga_horaria");
                    return new Cursos(nome, status, cargaHoraria);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar curso por nome: " + e.getMessage());
        }
        return null;
    }

    public boolean verificarExistenciaCurso(String nome) {
        String sql = "SELECT * FROM cursos WHERE nome = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência do curso: " + e.getMessage());
        }
        return false;
    }

    public void registrarNotaAluno(String nomeCurso, String nomeAluno, double nota) {
        String sql = "INSERT INTO notas (curso_nome, aluno_nome, nota) VALUES (?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nomeCurso);
            statement.setString(2, nomeAluno);
            statement.setDouble(3, nota);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao registrar nota do aluno: " + e.getMessage());
        }
    }

    public double calcularNotaMediaCurso(String nomeCurso) {
        String sql = "SELECT AVG(nota) AS media FROM notas WHERE curso_nome = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nomeCurso);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("media");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao calcular média das notas do curso: " + e.getMessage());
        }
        return 0.0;
    }

    public int quantidadeAlunosMatriculados(String nomeCurso) {
        String sql = "SELECT COUNT(*) AS total FROM notas WHERE curso_nome = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nomeCurso);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar alunos matriculados no curso: " + e.getMessage());
        }
        return 0;
    }

    public double calcularPorcentagemAprovados(String nomeCurso) {
        String sql = "SELECT COUNT(*) AS aprovados FROM notas WHERE curso_nome = ? AND nota >= 7";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nomeCurso);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int totalAlunos = quantidadeAlunosMatriculados(nomeCurso);
                    int aprovados = resultSet.getInt("aprovados");
                    return ((double) aprovados / totalAlunos) * 100;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao calcular porcentagem de alunos aprovados: " + e.getMessage());
        }
        return 0.0;
    }

    public double calcularPorcentagemReprovados(String nomeCurso) {
        return 100 - calcularPorcentagemAprovados(nomeCurso);
    }

    public void exibirEstatisticasDesempenho(String nomeCurso) {
        double media = calcularNotaMediaCurso(nomeCurso);
        int quantidadeAlunos = quantidadeAlunosMatriculados(nomeCurso);
        double porcentagemAprovados = calcularPorcentagemAprovados(nomeCurso);
        double porcentagemReprovados = calcularPorcentagemReprovados(nomeCurso);

        System.out.println("***** Estatísticas de Desempenho *****");
        System.out.println("Média das notas: " + media);
        System.out.println("Quantidade de alunos matriculados: " + quantidadeAlunos);
        System.out.println("Porcentagem de alunos aprovados: " + porcentagemAprovados + "%");
        System.out.println("Porcentagem de alunos reprovados: " + porcentagemReprovados + "%");
    }

    public void exibirQuantidadeAlunosMatriculados(String nomeCurso) {
        int quantidadeAlunos = quantidadeAlunosMatriculados(nomeCurso);
        System.out.println("Quantidade de alunos matriculados no curso '" + nomeCurso + "': " + quantidadeAlunos);
    }

    public void exibirNotaMediaGeral(String nomeCurso) {
        double media = calcularNotaMediaCurso(nomeCurso);
        System.out.println("Nota média geral dos alunos no curso '" + nomeCurso + "': " + media);
    }

    public void exibirPorcentagemAprovadosReprovados(String nomeCurso) {
        double porcentagemAprovados = calcularPorcentagemAprovados(nomeCurso);
        double porcentagemReprovados = calcularPorcentagemReprovados(nomeCurso);

        System.out.println("***** Porcentagem de Alunos Aprovados e Reprovados *****");
        System.out.println("Porcentagem de alunos aprovados: " + porcentagemAprovados + "%");
        System.out.println("Porcentagem de alunos reprovados: " + porcentagemReprovados + "%");
    }

    private Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("A conexão com o banco de dados não foi inicializada corretamente.");
        }
        return connection;
    }
}
