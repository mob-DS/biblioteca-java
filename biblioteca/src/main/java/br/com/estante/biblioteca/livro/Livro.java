package br.com.estante.biblioteca.livro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 120)
    private String autor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLeitura status = StatusLeitura.LENDO;

    @Column(length = 500)
    private String capaUrl;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    private String genero; 

    private Integer anoLancamento;

    private int capitulosTotal;

    private int capitulosLidos;

    private String tipoObra; // Ex: MANHWA, MANHUA, MANGA, NOVEL
    
    private String categoriaPrincipal; // Ex: Shounen, Seinen

    private boolean favorito = false; // Novo campo para marcar como favorito

    protected Livro() {
    }

    public Livro(String titulo, String autor, StatusLeitura status, String capaUrl, 
                 String sinopse, String genero, Integer anoLancamento, int capitulosTotal, 
                 int capitulosLidos, String tipoObra, String categoriaPrincipal, boolean favorito) {
        this.titulo = titulo;
        this.autor = autor;
        if (status != null) {
            this.status = status;
        }
        this.capaUrl = capaUrl;
        this.sinopse = sinopse;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.capitulosTotal = capitulosTotal;
        this.capitulosLidos = capitulosLidos;
        this.tipoObra = tipoObra;
        this.categoriaPrincipal = categoriaPrincipal;
        this.favorito = favorito;
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public StatusLeitura getStatus() { return status; }
    public void setStatus(StatusLeitura status) { this.status = status; }

    public String getCapaUrl() { return capaUrl; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }

    public int getCapitulosTotal() { return capitulosTotal; }
    public void setCapitulosTotal(int capitulosTotal) { this.capitulosTotal = capitulosTotal; }

    public int getCapitulosLidos() { return capitulosLidos; }
    public void setCapitulosLidos(int capitulosLidos) { this.capitulosLidos = capitulosLidos; }

    public String getTipoObra() { return tipoObra; }
    public void setTipoObra(String tipoObra) { this.tipoObra = tipoObra; }

    public String getCategoriaPrincipal() { return categoriaPrincipal; }
    public void setCategoriaPrincipal(String categoriaPrincipal) { this.categoriaPrincipal = categoriaPrincipal; }

    public boolean isFavorito() { return favorito; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }
}