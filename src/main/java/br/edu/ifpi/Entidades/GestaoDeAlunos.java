package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class GestaoDeAlunos {
    private List<Alunos> listaAlunos;

    public GestaoDeAlunos(){
        this.listaAlunos = new ArrayList<>();
    }

    public void cadastrarAluno(String nome, int id, String email, List<String> cursos){
        Alunos aluno = new Alunos(nome, id, email);
        aluno.getCursos().addAll(cursos);
        listaAlunos.add(aluno);
        System.out.println("Aluno cadastrado com sucesso.");
    }

    public void atualizarInformaçõesAluno(int id, String novoNome, String novoEmail, List<String> novosCursos){
        Alunos aluno = buscarAlunoPorId(id);
        if(aluno != null){
            aluno.nome = novoNome;
            aluno.email = novoEmail;
            aluno.getCursos().clear();
            aluno.getCursos().addAll(novosCursos);
            System.out.println("informações do aluno atualizadas com sucesso.");
        } else{
            System.out.println("Aluno não encontrado");
        }
    }

    public void visualizarPerfilAluno(int id){
        Alunos aluno = buscarAlunoPorId(id);
        if(aluno != null){
            System.out.println("Perfil do aLUNO: ");
            System.out.println("Nome: "+ aluno.getNome());
            System.out.println("ID: "+ aluno.getId());
            System.out.println("Email: "+ aluno.getEmail());
            System.out.println("Cursos Matriculados: "+ aluno.getCursos());
        } else{
            System.out.println("Aluno não encontrado.");
        }
    }

    public boolean cancelarMatricula(int id, String curso){
        Alunos aluno = buscarAlunoPorId(id);
        if(aluno != null){
            if (aluno.getCursos().contains(curso)) {
                aluno.getCursos().remove(curso);
                System.out.println("Matrícula cancelada com sucesso para o curso: " + curso);
                return true;
            } else {
                System.out.println("O aluno não está matriculado no curso especificado: " + curso);
                return false;
            }
        } else {
            System.out.println("Aluno não encontrado.");
            return false;
        }
    }

    private Alunos buscarAlunoPorId(int id){
        for (Alunos aluno : listaAlunos){
            if(aluno.getId() == id){
                return aluno;
            }
        }
        return null;
    }
}
