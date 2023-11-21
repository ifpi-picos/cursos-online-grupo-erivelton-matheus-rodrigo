package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static GestaoProfessores gestaoProfessores = new GestaoProfessores();
    private static GestaodeCursos gestaodeCursos = new GestaodeCursos();
    private static GestaoDeAlunos gestaoDeAlunos = new GestaoDeAlunos();

    public static void main(String[] args) {
        exibirMenu();
    }

    private static void exibirMenu() {
        int opcao;
        do {
            System.out.println("----- Menu -----");
            System.out.println("1. Cadastrar Professor");
            System.out.println("2. Associar Professor a Curso");
            System.out.println("3. Atualizar Informações do Professor");
            System.out.println("4. Visualizar Lista de Professores");
            System.out.println("5. Cadastrar Curso");
            System.out.println("6. Atualizar Curso");
            System.out.println("7. Visualizar Cursos");
            System.out.println("8. Cadastrar Aluno");
            System.out.println("9. Atualizar Informações do Aluno");
            System.out.println("10. Visualizar Perfil do Aluno");
            System.out.println("11. Cancelar Matrícula do Aluno");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProfessor();
                    break;
                case 2:
                    associarProfessorACurso();
                    break;
                case 3:
                    atualizarInformacoesProfessor();
                    break;
                case 4:
                    visualizarListaProfessores();
                    break;
                case 5:
                    cadastrarCurso();
                    break;
                case 6:
                    atualizarCurso();
                    break;
                case 7:
                    visualizarCursos();
                    break;
                case 8:
                    cadastrarAluno();
                    break;
                case 9:
                    atualizarInformacoesAluno();
                    break;
                case 10:
                    visualizarPerfilAluno();
                    break;
                case 11:
                    cancelarMatricula();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarProfessor() {
        System.out.println("Digite o nome do professor:");
        String nome = scanner.nextLine();
        System.out.println("Digite o ID do professor:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o e-mail do professor:");
        String email = scanner.nextLine();

        gestaoProfessores.cadastrarProfessor(nome, id, email);
        System.out.println("Professor cadastrado com sucesso.");
    }

    private static void associarProfessorACurso() {
        System.out.println("Digite o ID do professor:");
        int idProfessor = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o nome do curso:");
        String curso = scanner.nextLine();

        gestaoProfessores.associarProfessorACurso(idProfessor, curso);
    }

    private static void atualizarInformacoesProfessor() {
        System.out.println("Digite o ID do professor:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();
        System.out.println("Digite o novo e-mail:");
        String novoEmail = scanner.nextLine();

        gestaoProfessores.atualizarInformacoesProfessor(id, novoNome, novoEmail);
    }

    private static void visualizarListaProfessores() {
        gestaoProfessores.visualizarListaProfessores();
    }

    private static void cadastrarCurso() {
        System.out.println("Digite o nome do curso:");
        String nome = scanner.nextLine();
        System.out.println("Digite o status do curso:");
        String status = scanner.nextLine();
        System.out.println("Digite a carga horária do curso:");
        int cargaHoraria = scanner.nextInt();
        scanner.nextLine();

        gestaodeCursos.cadastrarCurso(nome, status, cargaHoraria);
        System.out.println("Curso cadastrado com sucesso.");
    }

    private static void atualizarCurso() {
        System.out.println("Digite o nome do curso:");
        String nome = scanner.nextLine();
        System.out.println("Digite o novo status do curso:");
        String novoStatus = scanner.nextLine();

        gestaodeCursos.atualizarCurso(nome, novoStatus);
    }

    private static void visualizarCursos() {
        gestaodeCursos.visualizarCursos();
    }

    private static void cadastrarAluno() {
        System.out.println("Digite o nome do aluno:");
        String nome = scanner.nextLine();
        System.out.println("Digite o ID do aluno:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o e-mail do aluno:");
        String email = scanner.nextLine();
        System.out.println("Digite os cursos matriculados (separados por vírgula):");
        String cursosStr = scanner.nextLine();
        List<String> cursos = new ArrayList<>();
        for (String curso : cursosStr.split(",")) {
            cursos.add(curso.trim());
        }

        gestaoDeAlunos.cadastrarAluno(nome, id, email, cursos);
    }

    private static void atualizarInformacoesAluno() {
        System.out.println("Digite o ID do aluno:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o novo nome:");
        String novoNome = scanner.nextLine();
        System.out.println("Digite o novo e-mail:");
        String novoEmail = scanner.nextLine();
        System.out.println("Digite os novos cursos matriculados (separados por vírgula):");
        String novosCursosStr = scanner.nextLine();
        List<String> novosCursos = new ArrayList<>();
        for (String curso : novosCursosStr.split(",")) {
            novosCursos.add(curso.trim());
        }

        gestaoDeAlunos.atualizarInformaçõesAluno(id, novoNome, novoEmail, novosCursos);
    }

    private static void visualizarPerfilAluno() {
        System.out.println("Digite o ID do aluno:");
        int id = scanner.nextInt();
        scanner.nextLine();

        gestaoDeAlunos.visualizarPerfilAluno(id);
    }

    private static void cancelarMatricula() {
        System.out.println("Digite o ID do aluno:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite o curso a ser cancelado:");
        String curso = scanner.nextLine();
    
        boolean matriculaCancelada = gestaoDeAlunos.cancelarMatricula(id, curso);
        if (matriculaCancelada) {
            System.out.println("Matrícula cancelada com sucesso para o curso: " + curso);
        } else {
            System.out.println("Não foi possível cancelar a matrícula para o curso: " + curso);
        }
    }    
}
