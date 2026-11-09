package br.com.estante.biblioteca.livro;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório JPA para a entidade Livro.
 * Fornece operações de banco de dados prontas sem precisar escrever SQL manual.
 */
public interface LivroRepository extends JpaRepository<Livro, Long> {
}