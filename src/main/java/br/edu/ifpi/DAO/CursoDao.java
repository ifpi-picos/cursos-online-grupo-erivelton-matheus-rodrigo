package br.edu.ifpi.DAO;

import br.edu.ifpi.Entidades.Cursos;
import br.edu.ifpi.Entidades.Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDao {
    private final Connection connection;
    private static final String INSERT_CURSO = "INSERT INTO cursos (nome, status, carga_horaria) VALUES (?, ?, ?)";
    private static final String UPDATE_CURSO_STATUS = "UPDATE cursos SET status = ? WHERE nome = ?";
    private static final String SELECT_ALL_CURSOS = "SELECT * FROM cursos";
    private static final String DELETE_CURSO = "DELETE FROM cursos WHERE nome = ?";
    private static final String SELECT_CURSO_BY_NOME = "SELECT * FROM cursos WHERE nome = ?";

    public CursoDao(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarCurso(String nome, String status, int cargaHoraria) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CURSO)) {
            statement.setString(1, nome);
            statement.setString(2, status);
            statement.setInt(3, cargaHoraria);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    public void atualizarCurso(String nome, String novoStatus) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CURSO_STATUS)) {
            statement.setString(1, novoStatus);
            statement.setString(2, nome);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public List<Cursos> listarCursosDisponiveis() {
        List<Cursos> cursos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CURSOS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String status = resultSet.getString("status");
                int cargaHoraria = resultSet.getInt("carga_horaria");
                Cursos curso = new Cursos(cargaHoraria, nome, status, cargaHoraria);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos disponíveis: " + e.getMessage());
        }
        return cursos;
    }

    public void deletarCurso(String nome) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CURSO)) {
            statement.setString(1, nome);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso: " + e.getMessage());
        }
    }

    public Cursos buscarCursoPorNome(String nome) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CURSO_BY_NOME)) {
            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    int cargaHoraria = resultSet.getInt("carga_horaria");
                    return new Cursos(cargaHoraria, nome, status, cargaHoraria);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso por nome: " + e.getMessage());
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

    public void registrarNotaAluno(String nomeCurso, int i, double nota) {
        String sql = "INSERT INTO notas (curso_nome, aluno_nome, nota) VALUES (?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nomeCurso);
            statement.setLong(2, i);
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

    public void adicionarProfessorNoCurso(Professor professor, String nomeCurso) {
        String sql = "INSERT INTO professor_curso (id_professor, nome_curso) VALUES (?, ?)";
        try (PreparedStatement stmt = Conexao.prepareStatement(sql)) {
            stmt.setInt(1, professor.getId());
            stmt.setString(2, nomeCurso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar professor ao curso: " + e.getMessage());
        }
    }

    public int obterIdCurso(String nomeCurso) {
        return 0;
    }

    public void registrarAlunoNoCurso(int idCurso, int id) {
    }

    public int obterIdPeloNome(String nomeCurso) {
        int idCurso = -1;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/", "postgres", "Cocarato05!")) {
            String query = "SELECT id FROM cursos WHERE nome = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeCurso);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idCurso = resultSet.getInt("id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCurso;
    }

    public boolean registrarNotaAluno(int idAluno, String nomeCurso, double nota) {
        String query = "INSERT INTO aluno_curso (id_aluno, nome_curso, nota) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = Conexao.prepareStatement(query)) {
            pstmt.setInt(1, idAluno);
            pstmt.setString(2, nomeCurso);
            pstmt.setDouble(3, nota);

            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    public List<Cursos> obterCursosConcluidos(int idAluno) throws SQLException {
        List<Cursos> cursosConcluidos = new ArrayList<>();
        
        String sql = "SELECT c.* FROM cursos c JOIN aluno_curso ac ON c.nome = ac.nome_curso WHERE ac.id_aluno = ? AND ac.status = 'concluído'";
        
        try (PreparedStatement stmt = Conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cursos curso = new Cursos(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("status"),
                        rs.getInt("carga_horaria")
                    );
                    cursosConcluidos.add(curso);
                }
            }
        }
        
        return cursosConcluidos;
    }

    public List<Cursos> obterCursosMatriculadosAluno(int idAluno) throws SQLException {
        List<Cursos> cursosMatriculados = new ArrayList<>();
        
        String sql = "SELECT c.* FROM cursos c JOIN cursos_alunos ac ON c.id = ac.id_curso WHERE ac.id_aluno = ? AND ac.status_curso = 'matriculado'";
        
        try (PreparedStatement stmt = Conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cursos curso = new Cursos(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("status"),
                        rs.getInt("carga_horaria")
                    );
                    cursosMatriculados.add(curso);
                }
            }
        }
        
        return cursosMatriculados;
    }
    

    public double calcularPorcentagemAproveitamentoAluno(int idAluno) throws SQLException {
        String sqlTotalCursos = "SELECT COUNT(*) FROM aluno_curso WHERE id_aluno = ?";
        String sqlCursosConcluidos = "SELECT COUNT(*) FROM aluno_curso WHERE id_aluno = ? AND status = 'concluído'";
        
        try (PreparedStatement stmtTotalCursos = Conexao.prepareStatement(sqlTotalCursos);
             PreparedStatement stmtCursosConcluidos = Conexao.prepareStatement(sqlCursosConcluidos)) {
            
            stmtTotalCursos.setInt(1, idAluno);
            try (ResultSet rsTotalCursos = stmtTotalCursos.executeQuery()) {
                if (rsTotalCursos.next()) {
                    int totalCursos = rsTotalCursos.getInt(1);
                    if (totalCursos == 0) {
                        return 0.0;
                    }
                    
                    stmtCursosConcluidos.setInt(1, idAluno);
                    try (ResultSet rsCursosConcluidos = stmtCursosConcluidos.executeQuery()) {
                        if (rsCursosConcluidos.next()) {
                            int cursosConcluidos = rsCursosConcluidos.getInt(1);
                            return (double) cursosConcluidos / totalCursos * 100;
                        }
                    }
                }
            }
        }
        
        return 0.0;
    }
}