CREATE TABLE professores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE cursos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    status VARCHAR(50),
    carga_horaria INT
);

CREATE TABLE turma (
    id_aluno INTEGER REFERENCES alunos(id),
    nome_curso VARCHAR(100) REFERENCES cursos(nome),
    nota_aluno FLOAT
);