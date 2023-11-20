package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class GestaodeCursos {
    private List<Curso> cursos;

    public GestaodeCursos(){
        this.cursos = new ArrayList<>();
    }

    public void cadastrarCurso(String nome, String status, int cargaHoraria){
        Curso curso = new Curso(nome, status, cargaHoraria);
        cursos.add(curso);
    }

    public void atualizarCurso(String nome, String novoStatus){
            for(Curso curso : cursos){
                if(curso.getNome().equals(nome)){
                    curso.setStatus(novoStatus);
                    break;
                }
            }
        }
    
    public void visualizarCursos(){
        for(Curso curso : cursos){
            System.out.println("Nome: "+curso.getNome());
            System.out.println("Status: "+ curso.getStatus());
            System.out.println("Carga Horária: "+ curso.getCargaHoraria());
            System.out.println("*****************************");
        }
    }
}