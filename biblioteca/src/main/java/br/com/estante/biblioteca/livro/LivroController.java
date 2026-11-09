package br.com.estante.biblioteca.livro;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável por expor os endpoints da API da Biblioteca.
 * Ele recebe as requisições HTTP (vindos do Postman ou de um front-end),
 * chama a camada de serviço e retorna as respostas formatadas em JSON.
 */
@RestController
@RequestMapping("/livros") // Define que todas as rotas aqui começam com /livros
public class LivroController {

    private final LivroService service;

    // Injeção de dependência do serviço via construtor
    public LivroController(LivroService service) {
        this.service = service;
    }

    /**
     * Endpoint para listar todas as obras cadastradas na estante.
     * Método HTTP: GET /livros
     */
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        List<Livro> livros = service.listarTodos();
        return ResponseEntity.ok(livros); // Retorna status 200 OK com a lista
    }

    /**
     * Endpoint para buscar uma obra específica pelo seu ID.
     * Método HTTP: GET /livros/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        Livro livro = service.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    /**
     * Endpoint para cadastrar uma nova obra (com capa, sinopse, capítulos, etc.).
     * Método HTTP: POST /livros
     */
    @PostMapping
    public ResponseEntity<Livro> cadastrar(@RequestBody @Valid LivroRequisicao dados) {
        Livro novoLivro = service.cadastrar(dados);
        // Retorna status 201 Created indicando que o recurso foi criado com sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }

    /**
     * Endpoint para atualizar os dados de uma obra já existente.
     * Método HTTP: PUT /livros/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody @Valid LivroRequisicao dados) {
        Livro livroAtualizado = service.atualizar(id, dados);
        return ResponseEntity.ok(livroAtualizado);
    }

    /**
     * Endpoint para excluir uma obra da estante pelo ID.
     * Método HTTP: DELETE /livros/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 No Content (sucesso sem corpo)
    }
}