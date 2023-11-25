package br.edu.ifpi.Entidades;

import java.util.List;
import java.util.Scanner;

import br.edu.ifpi.DAO.AlunoDao;
import br.edu.ifpi.DAO.ProfessorDao;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoDao alunoDao = new AlunoDao();
        ProfessorDao professorDao = new ProfessorDao();

        int opcao;
        do {
            System.out.println("***** Menu Principal *****");
            System.out.println("1 - Aluno");
            System.out.println("2 - Professor");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuAluno(scanner, alunoDao);
                    break;
                case 2:
                    menuProfessor(scanner, professorDao);
                    break;
                case 0:
                    System.out.println("Programa Encerrado!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida! Escolha uma opção válida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void menuAluno(Scanner scanner, AlunoDao alunoDao) {
        int opcao;
        do {
            System.out.println("***** Menu Aluno *****");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Atualizar Aluno");
            System.out.println("4 - Deletar Aluno");
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
                case 0:
                    System.out.println("Voltando para o Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma válida");
                    break;
            }
        } while (opcao != 0);
    }

    public static void menuProfessor(Scanner scanner, ProfessorDao professorDao) {
        int opcao;
        do {
            System.out.println("***** Menu Professor *****");
            System.out.println("1 - Listar Professores");
            System.out.println("2 - Inserir Professor");
            System.out.println("3 - Atualizar Professor");
            System.out.println("4 - Deletar Professor");
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
                case 0:
                    System.out.println("Voltando para o Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma opção válida");
                    break;
            }
        } while (opcao != 0);
    }
}