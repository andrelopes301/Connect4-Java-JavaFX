package jogo.iu.texto;

import jogo.logica.JogoMaqEstados;

public class IUTabuleiro {


    private JogoMaqEstados maqEstados;
    private final int lin = 6;
    private final int col = 7;

    public IUTabuleiro(JogoMaqEstados maqEstados) {
        this.maqEstados = maqEstados;
    }


    public void mostraTabuleiro() {

        System.out.println("\n           Tabuleiro do Jogo: ");
        for (int i = 0; i < lin; i++) {
            System.out.print("          ");
            for (int j = 0; j < col; j++) {
                System.out.printf("%c  ", maqEstados.getTabuleiro()[i][j]);
            }
            System.out.println();
        }
        System.out.print("          ");
        for (int i = 1; i <= col; i++) {
            System.out.printf("%d  ", i);
        }
        System.out.print("\n\n");
    }

    public void tabuleiroVazio(){

        for (int i = 0; i < lin; i++) {
            System.out.print("          ");
            for (int j = 0; j < col; j++) {
                System.out.print("_  ");
            }
            System.out.println();
        }
        System.out.print("\n\n");
    }


}
