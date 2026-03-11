package com.catalogo.filmes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "midia")
@Data
@NoArgsConstructor
public class Midia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String genero;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "classificacao_indicativa", length = 10)
    private String classificacaoIndicativa;

    @Column(name = "nota_media_calculada")
    private Double notaMediaCalculada;
}
