package br.com.estante.biblioteca.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequisicao(
    
    @NotBlank(message = "O titulo é obrigatorio")
    String titulo,

    @NotBlank(message = "O autor é obrigatorio")
    String autor,

    @NotNull(message = "O status é obrigatorio")
    StatusLeitura status,

    String capaUrl,
    String sinopse,
    String genero,
    Integer anoLancamento,
    int capitulosTotal,
    int capitulosLidos,
    String tipoObra,
    String categoriaPrincipal,
    boolean favorito
) {
}