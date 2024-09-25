import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tabuleiro extends JPanel {
    private static Tabuleiro instance;
    private static int MAXLIN = 20;
    private static int MAXCOL = 20;
    private static int NUMBOMBAS = 100;
    private ElementoBasico[][] celulas;

    private Tabuleiro() {
        super();
        // Cria o conjunto de células vazias e as insere no layout
        celulas = new ElementoBasico[MAXLIN][MAXCOL];
        this.setLayout(new GridLayout(MAXLIN, MAXCOL));
        for (int i = 0; i < MAXLIN; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                celulas[i][j] = new Fundo("Fundo[" + i + "][" + j + "]", i, j, this);
                this.add(celulas[i][j]);
            }
        }
    }

    public static Tabuleiro getInstance() {
        if (instance == null) {
            instance = new Tabuleiro();
        }
        return instance;
    }

    private static Map<String, ImageIcon> proxi = new HashMap<>();

    public static ImageIcon createImageIcon(String path) {
        if (proxi.containsKey(path)) {
            return proxi.get(path);
        }

        java.net.URL imgURL = App.class.getResource("imagens/" + path);
        if (imgURL != null) {
            ImageIcon aux = new ImageIcon(imgURL);
            aux = ElementoBasico.resize(aux, 32, 32);
            proxi.put(path, aux);
            return aux;
        } else {
            System.err.println("Couldn't find file: " + path);
            System.exit(0);
            return null;
        }
    }

    public static int getMaxlin() {
        return MAXLIN;
    }

    public static int getMaxcol() {
        return MAXCOL;
    }

    public static int getNumbombas() {
        return NUMBOMBAS;
    }

    public boolean posicaoValida(int lin, int col) {
        if ((lin < 0) || (col < 0) ||
                (lin >= MAXLIN) || (col >= MAXCOL)) {
            return false;
        } else {
            return true;
        }
    }

    // Retorna referencia em determinada posição
    public ElementoBasico getElementoNaPosicao(int lin, int col) {
        if (!posicaoValida(lin, col)) {
            return null;
        }
        return celulas[lin][col];
    }

    public ElementoBasico insereElemento(ElementoBasico elemento) {
        int lin = elemento.getLin();
        int col = elemento.getCol();
        if (!posicaoValida(lin, col)) {
            throw new IllegalArgumentException("Posicao invalida:" + lin + " ," + col);
        }
        ElementoBasico elementoAnterior = celulas[lin][col];
        celulas[lin][col] = elemento;
        return elementoAnterior;
    }

    public static void setDificuldade(int value) {
        switch (value) {
            case 0:
                NUMBOMBAS = 10;
                MAXLIN = 8;
                MAXCOL = 8;
                break;
            case 1:
                NUMBOMBAS = 40;
                MAXLIN = 16;
                MAXCOL = 16;
                break;
            case 2:
                NUMBOMBAS = 99;
                MAXLIN = 16;
                MAXCOL = 30;
                break;
        }
    }

    public void atualizaVisualizacao() {
        // Atualiza o conteúdo do JPanel (ver algo melhor)
        this.removeAll(); // erase everything from your JPanel
        this.revalidate();
        this.repaint();// I always do these steps after I modify my JPanel
        for (int i = 0; i < MAXLIN; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                this.add(celulas[i][j]);
            }
        }
    }

    public ArrayList<ElementoBasico> getVizinhos(int linhaAtual, int colunaAtual) {
        ArrayList<ElementoBasico> vizinhos = new ArrayList<>();

        // Verifica células vizinhas na vizinhança de Von Neumann (adjacentes
        // horizontalmente e verticalmente)
        int[][] bordas = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] casa : bordas) {
            int linhaVizinha = linhaAtual + casa[0];
            int colunaVizinha = colunaAtual + casa[1];

            // Verifica se a célula vizinha está dentro dos limites do tabuleiro
            if (linhaVizinha >= 0 && linhaVizinha < MAXLIN && colunaVizinha >= 0 && colunaVizinha < MAXCOL) {
                ElementoBasico vizinho = getElementoNaPosicao(linhaVizinha, colunaVizinha);
                vizinhos.add(vizinho);
            }
        }

        return vizinhos;
    }

    public void contarBombasVizinhas() {
        for (int linha = 0; linha < Tabuleiro.getMaxlin(); linha++) {
            for (int coluna = 0; coluna < Tabuleiro.getMaxcol(); coluna++) {
                ElementoBasico elemento = Tabuleiro.getInstance().getElementoNaPosicao(linha, coluna);
                if (elemento instanceof Fundo) {
                    Fundo fundo = (Fundo) elemento;
                    int numeroBombasVizinhas = 0;

                    for (ElementoBasico vizinho : Tabuleiro.getInstance().getVizinhos(linha, coluna)) {
                        if (vizinho instanceof Bomba) {
                            numeroBombasVizinhas++;
                        }
                    }

                    fundo.setNumeroBombas(numeroBombasVizinhas);
                }
            }
        }
    }
}
