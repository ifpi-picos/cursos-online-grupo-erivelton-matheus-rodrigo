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

    // Renomeado o parâmetro para evitar ambiguidade com o atributo cursos
    public void setCursos(List<Cursos> novosCursos){
        this.cursos = novosCursos;
    }

    public void adicionarCurso(Cursos curso) {
        this.cursos.add(curso);
    }

    public void adicionarNota(String cursoNome, double nota) {
        // Implemente a lógica para adicionar a nota do curso especificado pelo nome
    }

    public List<Double> getNotas() {
        // Implemente a lógica para retornar as notas do aluno
        return null;
    }

    public void removerCurso(String nomeCurso) {
        // Implemente a lógica para remover um curso pelo nome
    }
}