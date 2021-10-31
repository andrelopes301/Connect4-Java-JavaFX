package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class EsperaNome extends EstadoAdapter {

    public EsperaNome(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado sortearJogador(int modoJogo, String jogadorA, String jogadorB) {


        jogo.adicionaJogador(jogadorA,'O');
        jogo.adicionarLog("O Jogador "+jogo.getNome(0)+" foi adicionado!");
        jogo.adicionaJogador(jogadorB,'X');
        jogo.adicionarLog("O Jogador "+jogo.getNome(1)+" foi adicionado!");

        if(jogo.sortearJogador() == 1){
            jogo.adicionarLog("O Jogador sorteado a começar foi: "+jogo.getNome(0));
            return new EsperaJogadorA(jogo);   //se 1º jogador a jogar for jogador A
        } else{
            jogo.adicionarLog("O Jogador sorteado a começar foi: "+jogo.getNome(1));
            return new EsperaJogadorB(jogo);   //se 1º jogador a jogar for jogador B
        }
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.EsperaNome;
    }

    @Override
    public IEstado terminarJogo() {
        return new Inicio(jogo);
    }

}
