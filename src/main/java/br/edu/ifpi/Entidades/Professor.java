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

    public Professor(int professorId, String nome2, String email2) {
    }

    public String getNome(){
        return nome;
    }
    
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    public void atualizarInformacoes(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Email: " + email + " | Cursos: " + cursos;
    }
}