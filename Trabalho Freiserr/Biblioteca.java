import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Leitor> leitores = new ArrayList<>();
    private ArrayList<Livro> livros = new ArrayList<>();
    private ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    public boolean cadastrarLeitor(Leitor leitor) {
        if (leitor == null || consultarLeitor(leitor.getCodigo()) != null) return false;
        leitores.add(leitor);
        return true;
    }

    public Leitor consultarLeitor(int codigo) {
        for (Leitor leitor : leitores)
            if (leitor.getCodigo() == codigo) return leitor;
        return null;
    }

    public void listarLeitores() {
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor cadastrado.");
            return;
        }
        for (Leitor leitor : leitores) System.out.println(leitor);
    }

    public boolean removerLeitor(Leitor leitor) {
        if (leitor == null || !leitores.contains(leitor)) return false;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.isAberto() && emprestimo.getLeitor() == leitor) return false;
        }
        return leitores.remove(leitor);
    }

    public boolean cadastrarLivro(Livro livro) {
        if (livro == null || consultarLivro(livro.getCodigo()) != null) return false;
        livros.add(livro);
        return true;
    }

    public Livro consultarLivro(int codigo) {
        for (Livro livro : livros)
            if (livro.getCodigo() == codigo) return livro;
        return null;
    }

    public void listarLivros() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        for (Livro livro : livros) System.out.println(livro);
    }

    public boolean removerLivro(Livro livro) {
        if (livro == null || !livros.contains(livro)) return false;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.isAberto() && emprestimo.contemLivro(livro)) return false;
        }
        return livros.remove(livro);
    }

    public boolean registrarEmprestimo(int codigo, Leitor leitor, ArrayList<Livro> livrosSolicitados) {
        if (codigo <= 0 || consultarEmprestimo(codigo) != null
                || leitor == null || !leitores.contains(leitor)
                || livrosSolicitados == null || livrosSolicitados.isEmpty()) {
            return false;
        }

        // Validar todos os livros antes de alterar qualquer disponibilidade.
        ArrayList<Livro> validacao = new ArrayList<>();
        for (Livro livro : livrosSolicitados) {
            if (livro == null || !livros.contains(livro)
                    || !livro.isDisponivel() || validacao.contains(livro)) {
                return false;
            }
            validacao.add(livro);
        }

        Emprestimo emprestimo = new Emprestimo(codigo, leitor);
        for (Livro livro : validacao) {
            if (!emprestimo.adicionarLivro(livro)) return false;
        }
        for (Livro livro : validacao) livro.emprestar();

        emprestimos.add(emprestimo);
        return true;
    }

    private Emprestimo consultarEmprestimo(int codigo) {
        for (Emprestimo emprestimo : emprestimos)
            if (emprestimo.getCodigo() == codigo) return emprestimo;
        return null;
    }

    public void listarEmprestimos() {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }
        for (Emprestimo emprestimo : emprestimos) System.out.println(emprestimo);
    }

    public boolean registrarDevolucao(int codigo) {
        Emprestimo emprestimo = consultarEmprestimo(codigo);
        if (emprestimo == null || !emprestimo.isAberto()) return false;
        emprestimo.finalizar();
        return true;
    }
}
