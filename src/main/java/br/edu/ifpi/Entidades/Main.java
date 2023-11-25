package br.edu.ifpi.Entidades;

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
    
    }

    private static void menuProfessor(Scanner scanner, ProfessorDao professorDao) {
   
    }
}