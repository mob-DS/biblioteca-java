package br.com.estante.biblioteca.livro;

import java.util.List;
import org.springframework.stereotype.Service;
import br.com.estante.biblioteca.exception.LivroNaoEncontradoException;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public Livro cadastrar(LivroRequisicao dados) {
        Livro novo = new Livro(
            dados.titulo(), dados.autor(), dados.status(), 
            dados.capaUrl(), dados.sinopse(), dados.genero(), 
            dados.anoLancamento(), dados.capitulosTotal(), dados.capitulosLidos(),
            dados.tipoObra(), dados.categoriaPrincipal(), dados.favorito()
        );
        return repository.save(novo);
    }

    public Livro atualizar(Long id, LivroRequisicao dados) {
        Livro livro = buscarPorId(id);
        livro.setTitulo(dados.titulo());
        livro.setAutor(dados.autor());
        livro.setStatus(dados.status());
        livro.setCapaUrl(dados.capaUrl());
        livro.setSinopse(dados.sinopse());
        livro.setGenero(dados.genero());
        livro.setAnoLancamento(dados.anoLancamento());
        livro.setCapitulosTotal(dados.capitulosTotal());
        livro.setCapitulosLidos(dados.capitulosLidos());
        livro.setTipoObra(dados.tipoObra());
        livro.setCategoriaPrincipal(dados.categoriaPrincipal());
        livro.setFavorito(dados.favorito());
        return repository.save(livro);
    }

    public void excluir(Long id) {
        Livro livro = buscarPorId(id);
        repository.delete(livro);
    }
}