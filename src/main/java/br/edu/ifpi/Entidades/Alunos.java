package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Alunos {
    private String nome;
    private int id;
    private String email;
    private List<String> cursos;

    public Alunos(String nome, int id, String email) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.cursos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getCursos() {
        return cursos;
    }
}