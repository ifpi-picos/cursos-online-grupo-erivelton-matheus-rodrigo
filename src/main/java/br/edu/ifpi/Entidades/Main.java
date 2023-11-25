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
            System.out.println("1. Aluno");
            System.out.println("2. Professor");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuAluno(scanner, alunoDao);
                    break;
                case 2:
                    menuProfessor(scanner, professorDao);
                    break;
                case 3:
                    System.out.println("Programa Encerrado!");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma opção válida.");
                    break;
            }
        } while (opcao != 3);

        scanner.close();
    }

    private static void menuAluno(Scanner scanner, AlunoDao alunoDao) {
        int opcao;
        do {
            System.out.println("***** Menu Aluno *****");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Atualizar Aluno");
            System.out.println("4. Deletar Aluno");
            System.out.println("0. Voltar ao Menu Principal");
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
                    System.out.println("Voltando para o Menu Principal");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma válida");
                    break;
            }
        } while (opcao != 5);
    }

    private static void menuProfessor(Scanner scanner, ProfessorDao professorDao) {
   
    }
}