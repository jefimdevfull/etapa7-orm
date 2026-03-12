-- 1. Criação da tabela Usuario
CREATE TABLE usuario
(
    id            SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    data_cadastro DATE
);

-- 2. Criação da tabela Midia
CREATE TABLE midia
(
    id                       SERIAL PRIMARY KEY,
    titulo                   VARCHAR(150) NOT NULL,
    genero                   VARCHAR(50)  NOT NULL,
    ano_lancamento           INTEGER,
    classificacao_indicativa VARCHAR(10),
    nota_media_calculada     DOUBLE PRECISION
);

-- 3. Criação da tabela InteracaoUsuario (com chaves estrangeiras)
CREATE TABLE interacao_usuario
(
    id               SERIAL PRIMARY KEY,
    usuario_id       INTEGER NOT NULL,
    midia_id         INTEGER NOT NULL,
    status           VARCHAR(20),
    nota_pessoal     DOUBLE PRECISION,
    comentario       TEXT,
    data_atualizacao TIMESTAMP,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_midia FOREIGN KEY (midia_id) REFERENCES midia (id) ON DELETE CASCADE
);

-- 4. Inserindo dados iniciais para testes
INSERT INTO usuario (nome, email, data_cadastro)
VALUES ('Cícero Jeferson', 'cicero.jeferson@email.com', '2026-03-01'),
       ('Ana Paula', 'ana.paula@email.com', '2026-03-05');

INSERT INTO midia (titulo, genero, ano_lancamento, classificacao_indicativa, nota_media_calculada)
VALUES ('Dragon Ball Super: Broly', 'Animação', 2018, '10 anos', 8.5),
       ('Interestelar', 'Ficção Científica', 2014, '10 anos', 9.0);

INSERT INTO interacao_usuario (usuario_id, midia_id, status, nota_pessoal, comentario, data_atualizacao)
VALUES (1, 1, 'Assistido', 9.5, 'Filme excelente, animação impecável!', '2026-03-10 14:30:00'),
       (2, 2, 'Assistindo', NULL, 'Pausado na metade', '2026-03-11 09:15:00');