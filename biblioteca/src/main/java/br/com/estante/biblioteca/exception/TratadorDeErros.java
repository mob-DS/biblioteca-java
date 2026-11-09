package br.com.estante.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe global para capturar exceções lançadas na API e transformá-las
 * em respostas HTTP amigáveis (como 404 Not Found em vez de um erro 500 feio).
 */
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<String> tratarErro404(LivroNaoEncontradoException ex) {
        // Retorna o status HTTP 404 com a mensagem personalizada da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}