package com.catalogo.filmes;

import com.catalogo.filmes.model.InteracaoUsuario;
import com.catalogo.filmes.model.Midia;
import com.catalogo.filmes.repository.InteracaoUsuarioRepository;
import com.catalogo.filmes.repository.MidiaRepository;
import com.catalogo.filmes.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class FilmesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UsuarioRepository usuarioRepo, MidiaRepository midiaRepo, InteracaoUsuarioRepository interacaoRepo) {
		return args -> {
			System.out.println("\n=========================================================");
			System.out.println("Iniciando rotina de testes ORM (CRUD e Consultas)");
			System.out.println("=========================================================\n");

			// 1. CREATE (Inserindo 3 registros)
			System.out.println("[1] Operação CREATE: Inserindo novos registros de Midia");
			Midia m1 = new Midia();
			m1.setTitulo("Mr. Robot");
			m1.setGenero("Suspense");
			m1.setAnoLancamento(2015);
			m1.setClassificacaoIndicativa("16 anos");
			m1.setNotaMediaCalculada(9.0);

			Midia m2 = new Midia();
			m2.setTitulo("Dragon Ball Super: Super Hero");
			m2.setGenero("Animação");
			m2.setAnoLancamento(2022);
			m2.setClassificacaoIndicativa("12 anos");
			m2.setNotaMediaCalculada(8.5);

			Midia m3 = new Midia();
			m3.setTitulo("Matrix");
			m3.setGenero("Ficção Científica");
			m3.setAnoLancamento(1999);
			m3.setClassificacaoIndicativa("14 anos");
			m3.setNotaMediaCalculada(8.7);

			midiaRepo.saveAll(List.of(m1, m2, m3));
			System.out.println("-> Registros inseridos com sucesso.\n");

			// 2. READ (Listando dados)
			System.out.println("[2] Operação READ: Listando registros atuais");
			List<Midia> todasMidias = midiaRepo.findAll();
			todasMidias.forEach(m -> System.out.println(" - " + m.getTitulo() + " (" + m.getAnoLancamento() + ")"));
			System.out.println();

			// 3. UPDATE (Atualizando 1 registro)
			System.out.println("[3] Operação UPDATE: Atualizando registro existente");
			Midia midiaParaAtualizar = midiaRepo.findById(m1.getId()).orElse(null);
			if (midiaParaAtualizar != null) {
				midiaParaAtualizar.setTitulo("Mr. Robot (Série Completa)");
				midiaRepo.save(midiaParaAtualizar);
				System.out.println("-> Registro atualizado: " + midiaParaAtualizar.getTitulo() + "\n");
			}

			// 4. DELETE (Removendo 1 registro)
			System.out.println("[4] Operação DELETE: Removendo registro");
			midiaRepo.deleteById(m3.getId());
			System.out.println("-> Registro removido com sucesso.\n");

			// =========================================================
			// PARTE 4: CONSULTAS COM RELACIONAMENTO E FILTROS
			// =========================================================
			System.out.println("=========================================================");
			System.out.println("Executando Consultas Avançadas (Parte 4)");
			System.out.println("=========================================================\n");

			System.out.println("[Consulta 1] Filtro + Ordenação (Ficção Científica, ordenado por ano desc):");
			List<Midia> ficcao = midiaRepo.findByGeneroOrderByAnoLancamentoDesc("Ficção Científica");
			ficcao.forEach(m -> System.out.println(" - " + m.getTitulo() + " | Ano: " + m.getAnoLancamento()));
			System.out.println();

			System.out.println("[Consulta 2] JOIN: Interações do usuário especifico:");
			List<InteracaoUsuario> interacoesCicero = interacaoRepo.findByUsuarioNome("Cícero Jeferson");
			interacoesCicero.forEach(i -> System.out.println(" - Usuário interagiu com: " + i.getMidia().getTitulo() + " | Status: " + i.getStatus()));
			System.out.println();

			System.out.println("[Consulta 3] JOIN: Interações por gênero da mídia (Animação):");
			List<InteracaoUsuario> interacoesAnimacao = interacaoRepo.findByMidiaGenero("Animação");
			interacoesAnimacao.forEach(i -> System.out.println(" - Usuário: " + i.getUsuario().getNome() + " | Assistiu: " + i.getMidia().getTitulo()));
			System.out.println("\n=========================================================");
			System.out.println("Rotina finalizada com sucesso.");
			System.out.println("=========================================================\n");
		};
	}
}