package jogo.logica.dados;

import java.io.Serializable;

public class Jogador implements Serializable {

    private final String nome;
    private int creditos;
    private final char peca;
    private int pecaEspecial;

    public Jogador(String nome, char peca) {
        this.nome = nome;
        this.peca = peca;
        this.creditos = 5;
        pecaEspecial = 0;
    }


    public int getCreditos() {
        return creditos;
    }

    public char getPeca() {
        return peca;
    }

    public String getNome() {
        return nome;
    }

    public int getPecaEspecial() {
        return pecaEspecial;
    }

    public void decCreditos() {
       if (creditos > 0)
            creditos -= 1;
    }

    public void ganhaPecaEspecial(){
        pecaEspecial += 1;
    }

    public void usaPecaEspecial(){
        if (pecaEspecial > 0)
            pecaEspecial -= 1;
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "nome='" + nome + '\'' +
                ", creditos=" + creditos +
                ", peca=" + peca +
                ", pecaEspecial=" + pecaEspecial +
                '}';
    }
}



