package com.aluracursos.Back_fraseAleatoria.Repository;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class LibroDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Asegúrate de que haya una columna 'id' si estás usando generación automática

    @Column(unique = true)

    private String titulo;
    private String autor;
    private String idioma;
    private int numeroDescargas;

    public LibroDB() {
    }

    // Constructor con todos los campos excepto id
    public LibroDB(String titulo, String autor, String idioma, int numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
