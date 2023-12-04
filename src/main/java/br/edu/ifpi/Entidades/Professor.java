package br.edu.ifpi.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private String nome;
    private int id;
    private String email;
    private List<Cursos> cursosMinistrados;

    public Professor(String nome, int id, String email){
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.cursosMinistrados = new ArrayList<>();
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String novoNome) {
        this.nome = novoNome;
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

    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    public List<Cursos> getCursosMinistrados(){
        return cursosMinistrados;
    }

    public void adicionarCursoMinistrado(Cursos curso){
        cursosMinistrados.add(curso);
    }

    public void atualizarInformacoes(String novoNome, String novoEmail) {
        this.nome = novoNome;
        this.email = novoEmail;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Email: " + email + " | Cursos Ministrados: " + cursosMinistrados;
    }
}