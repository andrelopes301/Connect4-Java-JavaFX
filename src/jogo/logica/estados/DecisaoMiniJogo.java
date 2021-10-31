package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class DecisaoMiniJogo extends EstadoAdapter {

    private int jogador;

    public DecisaoMiniJogo(Jogo jogo, int jog) {
        super(jogo);
        this.jogador = jog;

    }
    @Override
    public IEstado decisao(int opcao){

        if(opcao == 0){
            if(jogador == 0)
                return new EsperaJogadorA(jogo);
            else
                return new EsperaJogadorB(jogo);
        }
        else {
            if (jogador == 0) {
                if (jogo.getMiniJogoJogadorA() == 0) {
                    jogo.setMiniJogoJogadorA(1);
                    return new MiniJogoPalavras(jogo, 0);
                } else if (jogo.getMiniJogoJogadorA() == 1) {
                    jogo.setMiniJogoJogadorA(0);
                    return new MiniJogoCalculos(jogo, 0);
                }
            } else if (jogador == 1) {
                if (jogo.getMiniJogoJogadorB() == 0) {
                    jogo.setMiniJogoJogadorB(1);
                    return new MiniJogoCalculos(jogo, 1);
                } else if (jogo.getMiniJogoJogadorB() == 1) {
                    jogo.setMiniJogoJogadorB(0);
                    return new MiniJogoPalavras(jogo, 1);
                }
            }

        }
        return this;

    }




    @Override
    public Situacao getSituacao() {
        return Situacao.DecisaoMiniJogo;
    }

}
