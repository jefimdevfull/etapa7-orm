package com.catalogo.filmes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "interacao_usuario")
@Data
@NoArgsConstructor
public class InteracaoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento N-1: Várias interações podem pertencer a um mesmo usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacionamento N-1: Várias interações podem ser da mesma mídia (filme/série)
    @ManyToOne
    @JoinColumn(name = "midia_id", nullable = false)
    private Midia midia;

    @Column(length = 20)
    private String status;

    @Column(name = "nota_pessoal")
    private Double notaPessoal;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
