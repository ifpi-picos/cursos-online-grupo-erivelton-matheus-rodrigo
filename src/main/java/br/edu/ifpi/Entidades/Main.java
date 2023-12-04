package br.edu.ifpi.Entidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import br.edu.ifpi.DAO.AlunoDao;
import br.edu.ifpi.DAO.AutenticacaoDao;
import br.edu.ifpi.DAO.CursoDao;
import br.edu.ifpi.DAO.ProfessorDao;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://db.wchdzdurkzceccavsubp.supabase.co:5432/postgres?user=postgres&password=Cocarato05!");
        
            AutenticacaoDao autenticacaoDao = new AutenticacaoDao(conexao);
            AlunoDao alunoDao = new AlunoDao(conexao);
            ProfessorDao professorDao = new ProfessorDao(conexao);
            CursoDao cursoDao = new CursoDao(conexao);
        
            int opcao;
            do {
                System.out.println("***** Menu Principal *****");
                System.out.println("1 - Cadastro de Aluno");
                System.out.println("2 - Cadastro de Professor");
                System.out.println("3 - Menu de Cursos");
                System.out.println("4 - Acesso de Aluno");
                System.out.println("5 - Acesso de Professor");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
        
                switch (opcao) {
                    case 1:
                        cadastrarAluno(scanner, alunoDao);
                        break;
                    case 2:
                        cadastrarProfessor(scanner, professorDao);
                        break;
                    case 3:
                        menuCursos(scanner, cursoDao, alunoDao);
                        break;
                    case 4:
                        acessarAluno(scanner, autenticacaoDao, alunoDao, cursoDao);
                        break;
                    case 5:
                        acessarProfessor(scanner, autenticacaoDao, professorDao, cursoDao);
                        break;
                    case 0:
                        System.out.println("Programa Encerrado!");
                        break;
                    default:
                        System.out.println("Opção inválida! Escolha uma opção válida.");
                        break;
                }
            } while (opcao != 0);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        }        
    }

    private static void cadastrarAluno(Scanner scanner, AlunoDao alunoDao) {
        scanner.nextLine();

        System.out.println("Digite o nome do aluno:");
        String nomeAluno = scanner.nextLine();
        System.out.println("Digite o email do aluno:");
        String emailAluno = scanner.nextLine();
        
        int novoIdAluno = alunoDao.obterNovoId();
        Alunos novoAluno = new Alunos(nomeAluno, novoIdAluno, emailAluno);
        int idNovoAluno = alunoDao.salvar(novoAluno);
        System.out.println("Aluno cadastrado com sucesso! ID: " + idNovoAluno);
    }

    private static void cadastrarProfessor(Scanner scanner, ProfessorDao professorDao) throws SQLException {
        scanner.nextLine();
    
        System.out.println("Digite o nome do professor:");
        String nomeProfessor = scanner.nextLine();
        System.out.println("Digite o email do professor:");
        String emailProfessor = scanner.nextLine();
    
        int novoIdProfessor = professorDao.obterNovoId();
        Professor novoProfessor = new Professor(nomeProfessor, novoIdProfessor, emailProfessor);
        int idNovoProfessor = professorDao.inserirProfessor(novoProfessor);
        System.out.println("Professor cadastrado com sucesso! ID: " + idNovoProfessor);
    }    

    private static void acessarAluno(Scanner scanner, AutenticacaoDao autenticacaoDao, AlunoDao alunoDao, CursoDao cursoDao) throws SQLException {
        System.out.println("Digite o email do aluno:");
        String emailAluno = scanner.next();
        System.out.println("Digite o ID do aluno:");
        int idAluno = scanner.nextInt();

        Alunos alunoAutenticado = autenticacaoDao.autenticarAluno(emailAluno, idAluno);

        if (alunoAutenticado != null) {
            menuAluno(scanner, alunoDao, alunoAutenticado, alunoAutenticado, cursoDao);
        } else {
            System.out.println("Autenticação falhou. Verifique suas credenciais.");
        }
    }

    private static void acessarProfessor(Scanner scanner, AutenticacaoDao autenticacaoDao, ProfessorDao professorDao, CursoDao cursoDao) throws SQLException {
        System.out.println("Digite o email do professor:");
        String emailProfessor = scanner.next();
        System.out.println("Digite o ID do professor:");
        int idProfessor = scanner.nextInt();
    
        Professor professorAutenticado = autenticacaoDao.autenticarProfessor(emailProfessor, idProfessor);
    
        if (professorAutenticado != null) {
            menuProfessor(scanner, professorDao, professorAutenticado, cursoDao);
        } else {
            System.out.println("Autenticação falhou. Verifique suas credenciais.");
        }
    }

    private static void menuAluno(Scanner scanner, AlunoDao alunoDao, Alunos alunoAutenticado, Alunos alunoAutenticado2, CursoDao cursoDao) {
        int opcao;
        do {
            System.out.println("***** Menu Aluno *****");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Atualizar Aluno");
            System.out.println("4 - Deletar Aluno");
            System.out.println("5 - Ver Estatísticas de Desempenho");
            System.out.println("6 - Matrícula do Aluno");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    scanner.nextLine();
                    System.out.println("Digite o nome do aluno:");
                    String nomeAluno = scanner.nextLine();
                    System.out.println("Digite o email do aluno:");
                    String emailAluno = scanner.nextLine();
                    alunoDao.salvar(new Alunos(nomeAluno, opcao, emailAluno));
                    break;
                case 2:
                    List<Alunos> alunos = alunoDao.listarTodos();
                    if (alunos.isEmpty()) {
                        System.out.println("Não há alunos cadastrados.");
                    } else {
                        System.out.println("***** Lista de Alunos *****");
                        for (Alunos aluno : alunos) {
                            System.out.println("ID: " + aluno.getId() + ", Nome: " + aluno.getNome() + ", Email: " + aluno.getEmail());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Digite o ID do aluno que deseja atualizar:");
                    int idAlunoAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o novo nome do aluno:");
                    String novoNomeAluno = scanner.nextLine();
                    System.out.println("Digite o novo email do aluno:");
                    String novoEmailAluno = scanner.nextLine();
                    Alunos alunoAtualizar = new Alunos(novoNomeAluno, idAlunoAtualizar, novoEmailAluno);
                    alunoAtualizar.setId(idAlunoAtualizar);
                    alunoDao.atualizar(alunoAtualizar);
                    break;
                case 4:
                    System.out.println("Digite o ID do aluno que deseja deletar:");
                    int idAlunoDeletar = scanner.nextInt();
                    alunoDao.deletar(idAlunoDeletar);
                    break;
                case 5:
                    exibirEstatisticasDesempenho((CursoDao) cursoDao, alunoAutenticado, "Nome do Curso");
                    break;
                case 6:
                    try {
                        matricularDesmatricularAluno(scanner, alunoDao, alunoAutenticado, cursoDao);
                    } catch (SQLException e) {
                        System.out.println("Erro ao executar operação de matrícula/desmatrícula: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Voltando para o Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma válida");
                    break;
            }
        } while (opcao != 0);
    }
    
    private static void menuProfessor(Scanner scanner, ProfessorDao professorDao, Professor professorAutenticado, CursoDao cursoDao) throws SQLException {
        int opcao;
        do {
            System.out.println("***** Menu Professor *****");
            System.out.println("1 - Listar Professores");
            System.out.println("2 - Inserir Professor");
            System.out.println("3 - Atualizar Professor");
            System.out.println("4 - Deletar Professor");
            System.out.println("5 - Adicionar Professor a um Curso");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    List<Professor> professores = professorDao.listarProfessores();
                    if (professores.isEmpty()) {
                        System.out.println("Não há professores cadastrados.");
                    } else {
                        System.out.println("***** Lista de Professores *****");
                        for (Professor professor : professores) {
                            System.out.println("ID: " + professor.getId() + ", Nome: " + professor.getNome() + ", Email: " + professor.getEmail());
                        }
                    }
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("Digite o nome do professor:");
                    String nomeProfessor = scanner.nextLine();
                    System.out.println("Digite o email do professor:");
                    String emailProfessor = scanner.nextLine();
                    professorDao.inserirProfessor(new Professor(nomeProfessor, opcao, emailProfessor));
                    break;
                case 3:
                    System.out.println("Digite o ID do professor que deseja atualizar:");
                    int idProfessorAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o novo nome do professor:");
                    String novoNomeProfessor = scanner.nextLine();
                    System.out.println("Digite o novo email do professor:");
                    String novoEmailProfessor = scanner.nextLine();
                    Professor professorAtualizar = new Professor(novoNomeProfessor, idProfessorAtualizar, novoEmailProfessor);
                    professorAtualizar.setId(idProfessorAtualizar);
                    professorDao.atualizarProfessor(professorAtualizar);
                    break;
                case 4:
                    System.out.println("Digite o ID do professor que deseja deletar:");
                    int idProfessorDeletar = scanner.nextInt();
                    professorDao.deletarProfessor(idProfessorDeletar);
                    break;
                case 5:
                    adicionarProfessorCurso(scanner, professorDao, professorAutenticado, cursoDao);
                    break;
                case 0:
                    System.out.println("Voltando para o Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma opção válida");
                    break;
            }
        } while (opcao != 0);
    }

    private static void menuCursos(Scanner scanner, CursoDao cursoDao, AlunoDao alunoDao) {
    int opcao;
    do {
        System.out.println("***** Menu de Cursos *****");
        System.out.println("1 - Cadastrar Curso");
        System.out.println("2 - Atualizar Curso");
        System.out.println("3 - Listar Cursos Disponíveis");
        System.out.println("4 - Exibir Quantidade de Alunos Matriculados no Curso");
        System.out.println("5 - Exibir Nota Média Geral dos Alunos no Curso");
        System.out.println("6 - Exibir Porcentagem de Alunos Aprovados");
        System.out.println("7 - Registrar Nota de Aluno em Curso");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                cadastrarCurso(scanner, cursoDao);
                break;
            case 2:
                atualizarCurso(scanner, cursoDao);
                break;
            case 3:
                listarCursos(cursoDao);
                break;
            case 4:
                exibirQuantidadeAlunosMatriculados(cursoDao);
                break;
            case 5:
                exibirNotaMediaGeral(cursoDao);
                break;
            case 6:
                exibirPorcentagemAprovados(scanner, cursoDao);
                break;
            case 7:
                registrarNotaAlunoCurso(scanner, alunoDao, cursoDao);
                break;
            case 0:
                System.out.println("Voltando para o Menu Principal");
                break;
            default:
                System.out.println("Opção inválida! Escolha uma opção válida");
                break;
        }
    } while (opcao != 0);
}

private static void cadastrarCurso(Scanner scanner, CursoDao cursoDao) {
    scanner.nextLine(); 
    System.out.println("Digite o nome do curso:");
    String nomeCurso = scanner.nextLine();
    System.out.println("Digite o status do curso (aberto/fechado):");
    String statusCurso = scanner.nextLine();
    System.out.println("Digite a carga horária do curso:");
    int cargaHoraria = scanner.nextInt();
    cursoDao.cadastrarCurso(nomeCurso, statusCurso, cargaHoraria);
    System.out.println("Curso cadastrado com sucesso!");
}

    private static void atualizarCurso(Scanner scanner, CursoDao cursoDao) {
        scanner.nextLine();
        System.out.println("Digite o nome do curso que deseja atualizar:");
        String nomeCurso = scanner.nextLine();
        System.out.println("Digite o novo status do curso (aberto/fechado):");
        String novoStatusCurso = scanner.nextLine();
        cursoDao.atualizarCurso(nomeCurso, novoStatusCurso);
        System.out.println("Curso atualizado com sucesso!");
    }

    private static void listarCursos(CursoDao cursoDao) {
        List<Cursos> cursos = cursoDao.listarCursosDisponiveis();
        if (cursos.isEmpty()) {
            System.out.println("Não há cursos disponíveis.");
        } else {
            System.out.println("***** Lista de Cursos Disponíveis *****");
            for (Cursos curso : cursos) {
                System.out.println("Nome: " + curso.getNome() + ", Status: " + curso.getStatus() + ", Carga Horária: " + curso.getCargaHoraria());
            }
        }
    }

    private static void exibirPorcentagemAprovados(Scanner scanner, CursoDao cursoDao) {
        scanner.nextLine();
        System.out.println("Digite o nome do curso:");
        String nomeCurso = scanner.nextLine();
        double porcentagemAprovados = cursoDao.calcularPorcentagemAprovados(nomeCurso);
        System.out.println("Porcentagem de alunos aprovados no curso '" + nomeCurso + "': " + porcentagemAprovados + "%");
    }

    private static void exibirEstatisticasDesempenho(CursoDao cursoDao, Alunos alunoAutenticado, String nomeCurso) {
        double media = cursoDao.calcularNotaMediaCurso(nomeCurso);
        int quantidadeAlunos = cursoDao.quantidadeAlunosMatriculados(nomeCurso);
        double porcentagemAprovados =  cursoDao.calcularPorcentagemAprovados(nomeCurso);
        double porcentagemReprovados = cursoDao.calcularPorcentagemReprovados(nomeCurso);

        System.out.println("***** Estatísticas de Desempenho *****");
        System.out.println("Média das notas: " + media);
        System.out.println("Quantidade de alunos matriculados: " + quantidadeAlunos);
        System.out.println("Porcentagem de alunos aprovados: " + porcentagemAprovados + "%");
        System.out.println("Porcentagem de alunos reprovados: " + porcentagemReprovados + "%");
    }

    private static void exibirQuantidadeAlunosMatriculados(CursoDao cursoDao) {
        int quantidadeAlunos = cursoDao.quantidadeAlunosMatriculados(null);
        System.out.println("Quantidade de alunos matriculados no curso: " + quantidadeAlunos);
    }    
    
    private static void exibirNotaMediaGeral(CursoDao cursoDao) {
        double media = cursoDao.calcularNotaMediaCurso(null);
        System.out.println("Nota média geral dos alunos no curso: " + media);
    }       
    
    private static void adicionarProfessorCurso(Scanner scanner, ProfessorDao professorDao, Professor professorAutenticado, CursoDao cursoDao) {
        System.out.println("Digite o nome do curso:");
        String nomeCurso = scanner.next();
    
        if (cursoDao.verificarExistenciaCurso(nomeCurso)) {
            cursoDao.adicionarProfessorNoCurso(professorAutenticado, nomeCurso);
            System.out.println("Professor adicionado ao curso '" + nomeCurso + "' com sucesso!");
        } else {
            System.out.println("O curso informado não existe.");
        }
    }    

    private static void matricularDesmatricularAluno(Scanner scanner, AlunoDao alunoDao, Alunos alunoAutenticado, CursoDao cursoDao) throws SQLException {
        System.out.println("***** Matrícula e Desmatrícula em Cursos *****");
        System.out.println("1 - Matricular em Curso");
        System.out.println("2 - Desmatricular de Curso");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcaoMatricula = scanner.nextInt();
    
        switch (opcaoMatricula) {
            case 1:
                matricularAluno(scanner, alunoDao, alunoAutenticado, cursoDao);
                break;
            case 2:
                desmatricularAluno(scanner, alunoDao, alunoAutenticado, cursoDao);
                break;
            case 0:
                System.out.println("Retornando ao Menu Principal...");
                break;
            default:
                System.out.println("Opção inválida! Escolha uma opção válida.");
                break;
        }
    }
    
    private static void matricularAluno(Scanner scanner, AlunoDao alunoDao, Alunos alunoAutenticado,CursoDao cursoDao) throws SQLException {
        scanner.nextLine();
        System.out.println("Digite o nome do curso que deseja se matricular:");
        String nomeCursoMatricula = scanner.nextLine();
    
        int idCursoMatricula = cursoDao.obterIdPeloNome(nomeCursoMatricula);
    
        if (idCursoMatricula != -1) {
            alunoDao.matricularAlunoNoCurso(alunoAutenticado.getId(), idCursoMatricula);
            System.out.println("Aluno matriculado com sucesso no curso: " + nomeCursoMatricula);
        } else {
            System.out.println("Curso não encontrado. Verifique o nome e tente novamente.");
        }
    }
    
    private static void desmatricularAluno(Scanner scanner, AlunoDao alunoDao, Alunos alunoAutenticado, Object cursoDao) throws SQLException {
        scanner.nextLine();
        System.out.println("Digite o nome do curso que deseja se desmatricular:");
        String nomeCursoDesmatricula = scanner.nextLine();
    
        int idCursoDesmatricula = ((CursoDao) cursoDao).obterIdPeloNome(nomeCursoDesmatricula);
    
        if (idCursoDesmatricula != -1) {
            alunoDao.desmatricularAlunoDoCurso(alunoAutenticado.getId(), idCursoDesmatricula);
            System.out.println("Aluno desmatriculado com sucesso do curso: " + nomeCursoDesmatricula);
        } else {
            System.out.println("Curso não encontrado. Verifique o nome e tente novamente.");
        }
    }

    private static void registrarNotaAlunoCurso(Scanner scanner, AlunoDao alunoDao, CursoDao cursoDao) {
        scanner.nextLine();
        System.out.println("Digite o ID do aluno:");
        int idAluno = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o nome do curso:");
        String nomeCurso = scanner.nextLine();
        System.out.println("Digite a nota do aluno no curso:");
        double nota = scanner.nextDouble();
    
        boolean resultado = cursoDao.registrarNotaAluno(idAluno, nomeCurso, nota);
        if (resultado) {
            System.out.println("Nota registrada com sucesso para o aluno no curso.");
        } else {
            System.out.println("Não foi possível registrar a nota. Verifique os dados fornecidos.");
        }
    }
}