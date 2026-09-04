# 💣 Campo Minado em Java

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen?style=for-the-badge)
![License](https://img.shields.io/badge/licen%C3%A7a-MIT-blue?style=for-the-badge)
![Nível](https://img.shields.io/badge/n%C3%ADvel-intermedi%C3%A1rio-yellow?style=for-the-badge)

Programa em Java que implementa uma versão em texto (console) do clássico jogo Campo Minado, feito para praticar o uso de matrizes.

---

## 📋 Descrição

O jogo gera um tabuleiro 8x8 e posiciona 10 bombas aleatoriamente, sem repetir posição. A cada rodada, o jogador escolhe uma linha e uma coluna para abrir:

- Se a célula tiver uma bomba, o jogo termina em derrota
- Se a célula for segura, é revelado o número de bombas nas 8 posições ao redor
- O jogo termina em vitória quando todas as células seguras forem abertas

O jogo usa três matrizes principais, todas do mesmo tamanho do tabuleiro:

| Matriz | Tipo | Função |
|---|---|---|
| `tabuleiro` | `char[][]` | O que o jogador vê (célula fechada, número ou bomba revelada) |
| `bombas` | `boolean[][]` | Posição real das bombas, oculta do jogador |
| `numerosVizinhos` | `int[][]` | Quantidade de bombas ao redor de cada célula |

## 💻 Código

```java
import java.util.Scanner;
import java.util.Random;

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
                System.out.println("Parabens, voce venceu!");
                return;
            }
        }

        exibirTabuleiro();
        System.out.println("Fim de jogo! Voce pisou em uma bomba.");
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
            System.out.println("Posicao invalida! Tente novamente.");
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
```

## ▶️ Como executar

```bash
javac CampoMinado.java
java CampoMinado
```

A cada rodada, digite a linha e a coluna da célula que deseja abrir. O jogo continua até você abrir uma bomba (derrota) ou abrir todas as células seguras (vitória).

## 📤 Exemplo de saída

```
   0 1 2 3 4 5 6 7 
 0 . . . . . . . . 
 1 . . . . . . . . 
 2 . . . . . . . . 
 3 . . . . . . . . 
 4 . . . . . . . . 
 5 . . . . . . . . 
 6 . . . . . . . . 
 7 . . . . . . . . 
Digite a linha: 3
Digite a coluna: 4
   0 1 2 3 4 5 6 7 
 0 . . . . . . . . 
 1 . . . . . . . . 
 2 . . . . . . . . 
 3 . . . . 2 . . . 
 4 . . . . . . . . 
 5 . . . . . . . . 
 6 . . . . . . . . 
 7 . . . . . . . . 
Digite a linha: 
```

## 🧠 Conceitos praticados

- Matrizes bidimensionais (`char[][]`, `boolean[][]`, `int[][]`) representando diferentes camadas de informação do mesmo tabuleiro
- Percorrer matrizes com loops `for` aninhados
- Verificação de vizinhança (checar as 8 posições ao redor de uma célula) com validação de limites do tabuleiro
- Geração de posições aleatórias sem repetição (`Random` + `while`)
- Conversão de `int` para `char` usando a tabela ASCII (`'0' + numero`)
- Leitura de entrada do usuário em loop (`Scanner`)
- Condições de fim de jogo (derrota e vitória) controlando o fluxo do `main`

## 🚧 Status do projeto

Este projeto está **concluído**. Principais funcionalidades implementadas:

- Tabuleiro 8x8 com posicionamento aleatório de 10 bombas
- Cálculo automático do número de bombas vizinhas para cada célula segura
- Jogada do usuário com validação de posição
- Detecção de derrota (célula com bomba) e vitória (todas as células seguras abertas)

## 🚀 Possíveis melhorias futuras

- Abertura em cascata de células vazias (revelar automaticamente os vizinhos quando a célula aberta não tem nenhuma bomba ao redor)
- Sistema de bandeiras para marcar suspeitas de bomba
- Tamanho do tabuleiro e quantidade de bombas configuráveis pelo jogador

---

<p align="center">Feito com ☕ e Java</p>
