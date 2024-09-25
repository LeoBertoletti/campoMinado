import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class App extends JFrame implements ActionListener {
    private Tabuleiro tabuleiro;
    Random r = new Random();

    public App() {
        super();
        // Define dificuldade
        Object[] opcoes = { "Fácil", "Médio", "Difícil" };

        int escolha = JOptionPane.showOptionDialog(null, "Selecione a dificuldade:", "Seletor",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        Tabuleiro.setDificuldade(escolha);

        // Cria o tabuleiro
        tabuleiro = Tabuleiro.getInstance();

        JPanel painelGeral = new JPanel();
        painelGeral.setLayout(new BoxLayout(painelGeral, BoxLayout.PAGE_AXIS));
        painelGeral.add(tabuleiro);

        for (int i = 0; i <= Tabuleiro.getNumbombas(); i++) {
            tabuleiro.insereElemento(
                    new Bomba("Bomba", r.nextInt(Tabuleiro.getMaxlin()), r.nextInt(Tabuleiro.getMaxcol()), tabuleiro));
        }

        Tabuleiro.getInstance().contarBombasVizinhas();

        // Exibe a janela
        this.add(painelGeral);
        this.setTitle("Campo Minado");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        tabuleiro.atualizaVisualizacao();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        tabuleiro.atualizaVisualizacao();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}