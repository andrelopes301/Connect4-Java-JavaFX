package jogo.logica.estados;

import jogo.logica.Situacao;

public interface IEstado {


    IEstado iniciarJogo();
    IEstado escolherNomes(int modoJogo);
    IEstado sortearJogador(int modoJogo, String jogadorA, String jogadorB);
    IEstado escolherColuna(int coluna);
    IEstado ganhouMiniJogoCalculos(boolean ganhou);
    IEstado ganhouMiniJogoPalavras(boolean ganhou);
    IEstado jogarPecaEspecial(int coluna, int jogador);
    IEstado decisao(int opcao);
    IEstado voltarAtras();
    IEstado replayJogada();
    IEstado terminarJogo();



    Situacao getSituacao();


}
