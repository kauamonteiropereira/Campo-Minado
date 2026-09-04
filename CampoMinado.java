import java.util.Random;
import java.util.Scanner;

public class CampoMinado {

    static final int LINHAS = 8;
    static final int COLUNAS = 8;
    static final int QTD_BOMBAS = 10;

    static char[][] tabuleiro = new char[LINHAS][COLUNAS];
    static boolean[][] bombas = new boolean[LINHAS][COLUNAS];
    static int[][] numerosVizinhos = new int[LINHAS][COLUNAS];
    static Scanner scanner = new Scanner(System.in);

        public static void main(String[] args) {
        inicializarTabuleiro();
        posicionarBombas();
        calcularVizinhos();

        boolean jogoContinua = true;

        while (jogoContinua) {
            exibirTabuleiro();
            jogoContinua = jogar();

            if (jogoContinua && venceu()) {
                exibirTabuleiro();
                System.out.println("Parabéns, você venceu!");
                return;
            }
        }

        exibirTabuleiro();
        System.out.println("Fim de jogo! Você pisou em uma bomba.");
    }

    static void inicializarTabuleiro() {
        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                tabuleiro[linha][coluna] = '.';
            }
        }
    }

    static void exibirTabuleiro() {
        System.out.print("   ");
        for (int coluna = 0; coluna < COLUNAS; coluna++) {
            System.out.print(coluna + " ");
        }
        System.out.println();

        for (int linha = 0; linha < LINHAS; linha++) {
            System.out.printf("%2d ", linha);
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                System.out.print(tabuleiro[linha][coluna] + " ");
            }
            System.out.println();
        }
    }

    static void posicionarBombas() {
        Random random = new Random();
        int bombasColocadas = 0;

        while (bombasColocadas < QTD_BOMBAS) {
            int linha = random.nextInt(LINHAS);
            int coluna = random.nextInt(COLUNAS);
            if (!bombas[linha][coluna]) {
                bombas[linha][coluna] = true;
                bombasColocadas++;
            }
        }
    }

    static void calcularVizinhos() {
        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                if (!bombas[linha][coluna]) {
                    numerosVizinhos[linha][coluna] = contarBombasVizinhas(linha, coluna);
                }
            }
        }
    }

    static int contarBombasVizinhas(int linha, int coluna) {
        int contador = 0;
        for (int l = linha - 1; l <= linha + 1; l++) {
            for (int c = coluna - 1; c <= coluna + 1; c++) {
                if (posicaoValida(l, c) && bombas[l][c]) {
                    contador++;
                }
            }
        }
        return contador;
    }

    static boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < LINHAS && coluna >= 0 && coluna < COLUNAS;
    }

    static boolean jogar() {
        System.out.print("Digite a linha: ");
        int linha = scanner.nextInt();
        System.out.print("Digite a coluna: ");
        int coluna = scanner.nextInt();

        if (!posicaoValida(linha, coluna)) {
            System.out.println("Posição inválida! Tente novamente.");
            return true;
        }

        return abrirCelula(linha, coluna);
    }

    static boolean abrirCelula(int linha, int coluna) {
        if (bombas[linha][coluna]) {
            tabuleiro[linha][coluna] = '*';
            return false;
        }

        tabuleiro[linha][coluna] = (char) ('0' + numerosVizinhos[linha][coluna]);
        return true;
    }

    static boolean venceu() {
        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                boolean celulaFechada = tabuleiro[linha][coluna] == '.';
                if (celulaFechada && !bombas[linha][coluna]) {
                    return false;
                }
            }
        }
        
        return true; 
    }
}