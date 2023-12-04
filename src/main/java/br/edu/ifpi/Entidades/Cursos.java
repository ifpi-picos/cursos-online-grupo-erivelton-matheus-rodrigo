package br.edu.ifpi.Entidades;

public class Cursos {
    private String nome;
    private String status;
    private int cargaHoraria;

    public Cursos(int id,String nome, String status, int cargaHoraria) {
        this.nome = nome;
        this.status = status;
        this.cargaHoraria = cargaHoraria;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    public String getId() {
        return null;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", status='" + status + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                '}';
    }
}
