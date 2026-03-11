package com.catalogo.filmes.repository;

import com.catalogo.filmes.model.InteracaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteracaoUsuarioRepository extends JpaRepository<InteracaoUsuario, Long> {

    // Consulta 2 (Relacionamento/JOIN): Buscar todas as interações de um usuário específico pelo Nome
    List<InteracaoUsuario> findByUsuarioNome(String nomeUsuario);

    // Consulta 3 (Relacionamento/JOIN): Buscar interações baseadas no gênero da mídia
    List<InteracaoUsuario> findByMidiaGenero(String genero);
}