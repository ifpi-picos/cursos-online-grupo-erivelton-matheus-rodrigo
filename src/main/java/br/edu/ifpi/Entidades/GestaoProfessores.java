package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class GestaoProfessores {
    private List<Professor> professores;

    public GestaoProfessores(){
        this.professores = new ArrayList<>();
    }

    public void cadastrarProfessor(String nome, int id, String email){
        Professor professor = new Professor(nome, id, email);
        professores.add(professor);
    }

    public void associarProfessorACurso(int idProfessor, String curso){
        Professor professor = buscarProfessorPorId(idProfessor);
        if(professor != null){
            professor.adicionarCurso(curso);
            System.out.println("Professor associado ao curso com sucesso.");
        }else{
            System.out.println("Professor não encontrado");
        }
    }

    public void atualizarInformacoesProfessor(int id, String novoNome, String novoEmail){
        Professor professor = buscarProfessorPorId(id);
        if(professor != null){
            professor.setNome(novoNome);
            professor.setEmail(novoEmail);
            System.out.println("informações do professor atualizadas com sucesso.");
        }else{
            System.out.println("Professor não encontrado");
        }
    }

    public void visualizarListaProfessores(){
        System.out.println("Lista de Professores:");
        for(Professor professor : professores){
            System.out.println("Nome: "+ professor.getNome());
            System.out.println("ID: "+ professor.getId());
            System.out.println("Email: "+ professor.getEmail());
            System.out.println("Cursos que ministra: "+ professor.getCursos());
        }
    }

    private Professor buscarProfessorPorId(int id){
        for(Professor professor : professores){
            if(professor.getId() == id){
                return professor;
            }
        }
        return null;
    }
}