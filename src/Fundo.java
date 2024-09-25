public class Fundo extends ElementoBasico {
    private boolean aberto = false;
    private int numeroBombas = 0;

    public Fundo(String id, int linInicial, int colInicial, Tabuleiro tabuleiro) {
        super(id, "fechado.jpg", linInicial, colInicial, tabuleiro);
    }

    public void abrir() {
        if (!aberto) {
            this.aberto = true;
            switch (numeroBombas) {
                case 1:
                    this.setIcon(Tabuleiro.createImageIcon("1.png"));
                    break;
                case 2:
                    this.setIcon(Tabuleiro.createImageIcon("2.png"));
                    break;
                case 3:
                    this.setIcon(Tabuleiro.createImageIcon("3.png"));
                    break;
                case 4:
                    this.setIcon(Tabuleiro.createImageIcon("4.png"));
                    break;
                case 5:
                    this.setIcon(Tabuleiro.createImageIcon("5.png"));
                    break;
                case 6:
                    this.setIcon(Tabuleiro.createImageIcon("6.png"));
                    break;
                case 7:
                    this.setIcon(Tabuleiro.createImageIcon("7.png"));
                    break;
                case 8:
                    this.setIcon(Tabuleiro.createImageIcon("8.png"));
                    break;
                default:
                    this.setIcon(Tabuleiro.createImageIcon("aberto.jpg"));
                    break;
            }

            if (numeroBombas == 0) {
                // Recursivamente abre as células vazias adjacentes
                for (ElementoBasico vizinho : Tabuleiro.getInstance().getVizinhos(this.getLin(), this.getCol())) {
                    if (vizinho instanceof Fundo) {
                        Fundo vizinhoFundo = (Fundo) vizinho;
                        vizinhoFundo.abrir();
                    }
                }
            }
        }
    }

    public void setNumeroBombas(int numeroBombas) {
        this.numeroBombas = numeroBombas;
    }

    public int getNumeroBombas() {
        return numeroBombas;
    }

    @Override
    public void acao(ElementoBasico outro) {
        this.abrir();
    }
}
