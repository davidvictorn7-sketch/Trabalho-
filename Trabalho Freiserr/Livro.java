public class Livro {
    private int codigo;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(int codigo, String titulo, String autor) {
        if (codigo <= 0) throw new IllegalArgumentException("Código deve ser positivo.");
        validarTexto(titulo, "Título");
        validarTexto(autor, "Autor");
        this.codigo = codigo;
        this.titulo = titulo.trim();
        this.autor = autor.trim();
        this.disponivel = true;
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty())
            throw new IllegalArgumentException(campo + " não pode ficar vazio.");
    }

    public int getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isDisponivel() { return disponivel; }

    public void atualizarDados(String titulo, String autor) {
        validarTexto(titulo, "Título");
        validarTexto(autor, "Autor");
        this.titulo = titulo.trim();
        this.autor = autor.trim();
    }

    public void emprestar() {
        disponivel = false;
    }

    public void devolver() {
        disponivel = true;
    }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + autor + ") - "
                + (disponivel ? "disponível" : "indisponível");
    }
}
