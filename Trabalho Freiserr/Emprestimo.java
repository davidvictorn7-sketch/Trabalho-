import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Emprestimo {
    private int codigo;
    private LocalDate dataEmprestimo;
    private boolean aberto;
    private Leitor leitor;
    private ArrayList<ItemEmprestimo> itens;

    public Emprestimo(int codigo, Leitor leitor) {
        if (codigo <= 0) throw new IllegalArgumentException("Código deve ser positivo.");
        if (leitor == null) throw new IllegalArgumentException("Leitor inválido.");
        this.codigo = codigo;
        this.leitor = leitor;
        this.dataEmprestimo = LocalDate.now();
        this.aberto = true;
        this.itens = new ArrayList<>();
    }

    public int getCodigo() { return codigo; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public boolean isAberto() { return aberto; }
    public Leitor getLeitor() { return leitor; }
    public ArrayList<ItemEmprestimo> getItens() {
        return new ArrayList<>(itens);
    }

    public boolean adicionarLivro(Livro livro) {
        if (livro == null || contemLivro(livro) || !livro.isDisponivel()) {
            return false;
        }
        itens.add(new ItemEmprestimo(livro));
        return true;
    }

    public boolean contemLivro(Livro livro) {
        for (ItemEmprestimo item : itens) {
            if (item.getLivro() == livro) return true;
        }
        return false;
    }

    public void finalizar() {
        if (!aberto) return;
        for (ItemEmprestimo item : itens) {
            item.registrarDevolucao();
            item.getLivro().devolver();
        }
        aberto = false;
    }

    @Override
    public String toString() {
        StringBuilder nomes = new StringBuilder();
        for (ItemEmprestimo item : itens) {
            if (nomes.length() > 0) nomes.append(", ");
            nomes.append(item.getLivro().getTitulo());
        }
        String data = dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return codigo + " - " + leitor.getNome() + " - " + data + " - "
                + (aberto ? "aberto" : "finalizado") + " - livros: " + nomes;
    }
}
