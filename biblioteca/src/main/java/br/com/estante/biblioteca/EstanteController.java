package br.com.estante.biblioteca;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstanteController {

    @GetMapping({"/", "/estante"})
    public String exibirEstante() {
        return "livros/Index";
    }
}
