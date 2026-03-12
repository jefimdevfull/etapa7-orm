------------------------------------------------------------------
-- PROJETO FINAL: SISTEMA DE CATÁLOGO DE FILMES
-- ALUNO: CÍCERO JEFERSON
-- ETAPA: FINAL (DDL, DML, VIEWS, TRIGGERS, PROCEDURES E EXPLAIN)
------------------------------------------------------------------

-- [1] DDL - CRIAÇÃO DAS TABELAS E INTEGRIDADE
DROP TABLE IF EXISTS interacao_usuario;
DROP TABLE IF EXISTS midia;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    data_cadastro DATE DEFAULT CURRENT_DATE
);

CREATE TABLE midia (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    ano_lancamento INTEGER,
    classificacao_indicativa VARCHAR(10),
    nota_media_calculada DOUBLE PRECISION DEFAULT 0
);

CREATE TABLE interacao_usuario (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL,
    midia_id INTEGER NOT NULL,
    status VARCHAR(20) CHECK (status IN ('Assistido', 'Assistindo', 'Quero Ver', 'Abandonado')),
    nota_pessoal DOUBLE PRECISION CHECK (nota_pessoal >= 0 AND nota_pessoal <= 10),
    comentario TEXT,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    CONSTRAINT fk_midia FOREIGN KEY (midia_id) REFERENCES midia (id) ON DELETE CASCADE
);

-- [2] DML - INSERÇÃO DE DADOS PARA TESTE
INSERT INTO usuario (nome, email) VALUES
('Cícero Jeferson', 'cicero@email.com'),
('Ana Paula', 'ana@email.com');

INSERT INTO midia (titulo, genero, ano_lancamento, classificacao_indicativa) VALUES
('Dragon Ball Super: Broly', 'Animação', 2018, '10 anos'),
('Interestelar', 'Ficção Científica', 2014, '10 anos'),
('O Auto da Compadecida', 'Comédia', 2000, 'Livre');

INSERT INTO interacao_usuario (usuario_id, midia_id, status, nota_pessoal, comentario) VALUES
(1, 1, 'Assistido', 10, 'Melhor luta da franquia!'),
(2, 2, 'Assistido', 9.5, 'Obra prima visual.'),
(1, 3, 'Quero Ver', NULL, 'Dica de um amigo.');

-- [3] VIEWS (RECURSOS AVANÇADOS)
-- View que mostra o resumo das avaliações dos usuários
CREATE OR REPLACE VIEW v_resumo_interacoes AS
SELECT 
    u.nome AS usuario, 
    m.titulo AS filme, 
    i.status, 
    i.nota_pessoal
FROM interacao_usuario i
JOIN usuario u ON i.usuario_id = u.id
JOIN midia m ON i.midia_id = m.id;

-- [4] PROCEDURES
-- Procedure para atualizar o status de uma interação de forma segura
CREATE OR REPLACE PROCEDURE pr_atualizar_status(p_interacao_id INT, p_novo_status VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE interacao_usuario 
    SET status = p_novo_status, data_atualizacao = CURRENT_TIMESTAMP
    WHERE id = p_interacao_id;
    COMMIT;
END;
$$;

-- [5] TRIGGERS
-- Trigger para atualizar a nota_media_calculada da mídia sempre que houver nova avaliação
CREATE OR REPLACE FUNCTION fn_atualiza_media_midia()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE midia
    SET nota_media_calculada = (
        SELECT AVG(nota_pessoal) 
        FROM interacao_usuario 
        WHERE midia_id = NEW.midia_id AND nota_pessoal IS NOT NULL
    )
    WHERE id = NEW.midia_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_atualiza_nota_midia
AFTER INSERT OR UPDATE ON interacao_usuario
FOR EACH ROW EXECUTE FUNCTION fn_atualiza_media_midia();

-- [6] CONSULTAS COM EXPLAIN / EXPLAIN ANALYZE
-- Analisando a performance da busca por gênero (relevante para filtros)
EXPLAIN ANALYZE 
SELECT * FROM midia WHERE genero = 'Animação';

-- Analisando o custo do JOIN entre usuários e suas interações
EXPLAIN 
SELECT u.nome, i.comentario 
FROM usuario u 
INNER JOIN interacao_usuario i ON u.id = i.usuario_id;

-- [7] TESTE FINAL DA PROCEDURE
CALL pr_atualizar_status(3, 'Assistindo');

-- Final do Script
