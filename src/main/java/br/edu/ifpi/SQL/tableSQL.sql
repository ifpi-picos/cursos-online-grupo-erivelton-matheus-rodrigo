create table
  public.aluno_curso (
    id serial,
    id_aluno integer null,
    nota double precision null,
    nome_curso text null,
    id_curso integer null,
    status_curso character varying(50) null,
    constraint aluno_curso_pkey primary key (id),
    constraint aluno_curso_id_aluno_fkey foreign key (id_aluno) references alunos (id),
    constraint aluno_curso_id_curso_fkey foreign key (id_curso) references cursos (id),
    constraint aluno_curso_nome_curso_fkey foreign key (nome_curso) references cursos (nome)
  ) tablespace pg_default;

  create table
  public.alunos (
    id serial,
    nome character varying(100) null,
    email character varying(100) null,
    nota double precision not null,
    status character varying(50) null,
    constraint alunos_pkey primary key (id)
  ) tablespace pg_default;

  create table
  public.cursos (
    id serial,
    nome character varying(100) null,
    status character varying(50) not null,
    carga_horaria integer null,
    constraint cursos_pkey primary key (id),
    constraint unique_nome unique (nome),
    constraint fk_curso_id foreign key (id) references cursos (id)
  ) tablespace pg_default;

  create table
  public.cursos_alunos (
    id serial,
    id_aluno integer null,
    id_curso integer null,
    status_curso character varying(50) null,
    porcentagem_aproveitamento numeric(5, 2) null,
    constraint cursos_alunos_pkey primary key (id),
    constraint cursos_alunos_id_aluno_fkey foreign key (id_aluno) references alunos (id),
    constraint cursos_alunos_id_curso_fkey foreign key (id_curso) references cursos (id)
  ) tablespace pg_default;

  create table
  public.notas (
    id serial,
    id_aluno integer null,
    id_curso integer null,
    nota double precision null,
    nome character varying null,
    constraint notas_pkey primary key (id),
    constraint notas_id_aluno_fkey foreign key (id_aluno) references alunos (id),
    constraint notas_id_curso_fkey foreign key (id_curso) references cursos (id),
    constraint notas_nome_fkey foreign key (nome) references cursos (nome)
  ) tablespace pg_default;

  create table
  public.professor_curso (
    id serial,
    id_professor integer null,
    nome_curso character varying(100) null,
    id_curso integer null,
    constraint professores_curso_pkey primary key (id),
    constraint professor_curso_id_curso_fkey foreign key (id_curso) references cursos (id),
    constraint professor_curso_id_professor_fkey foreign key (id_professor) references professores (id)
  ) tablespace pg_default;

  create table
  public.professores (
    id serial,
    nome character varying(100) null,
    email character varying(100) null,
    constraint professores_pkey primary key (id)
  ) tablespace pg_default;