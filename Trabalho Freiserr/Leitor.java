public class Leitor {
    private int codigo;
    private String nome;
    private String email;

    public Leitor(int codigo, String nome, String email) {
        if (codigo <= 0) throw new IllegalArgumentException("Código deve ser positivo.");
        validarTexto(nome, "Nome");
        validarTexto(email, "E-mail");
        this.codigo = codigo;
        this.nome = nome.trim();
        this.email = email.trim();
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty())
            throw new IllegalArgumentException(campo + " não pode ficar vazio.");
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    public void atualizarDados(String nome, String email) {
        validarTexto(nome, "Nome");
        validarTexto(email, "E-mail");
        this.nome = nome.trim();
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + email + ")";
    }
}
