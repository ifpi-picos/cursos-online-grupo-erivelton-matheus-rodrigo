package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private String nome;
    private int id;
    private String email;
    private List<String> cursos;

    public Professor(String nome, int id, String email){
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.cursos = new ArrayList<>();
    }

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    public List<String> getCursos(){
        return cursos;
    }

    public void adicionarCurso(String curso){
        cursos.add(curso);
    }
}