package com.catalogo.filmes.repository;

import com.catalogo.filmes.model.Midia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MidiaRepository extends JpaRepository<Midia, Long> {

    // Consulta 1 (Filtro + Ordenação): Buscar filmes por gênero e ordenar do mais novo pro mais velho
    List<Midia> findByGeneroOrderByAnoLancamentoDesc(String genero);
}
