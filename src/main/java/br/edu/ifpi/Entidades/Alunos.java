package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Alunos {
    protected String nome;
    private int id;
    protected String email;
    private List<Cursos> cursos; 

    public Alunos(String nome, int id, String email) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.cursos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public List<Cursos> getCursos() {
        return cursos;
    }

    public void setCursos(List<Cursos> novosCursos){
        this.cursos = novosCursos;
    }
}