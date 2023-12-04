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

CREATE TABLE professor_curso (
    id SERIAL PRIMARY KEY,
    id_professor INTEGER REFERENCES professores(id),
    nome_curso VARCHAR(100)
);

CREATE TABLE aluno_curso (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER REFERENCES alunos(id),
    id_curso INTEGER REFERENCES cursos(id),
    UNIQUE (id_aluno, id_curso)
);