package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class MiniJogoCalculos extends EstadoAdapter {

    private int jogador;

    public MiniJogoCalculos(Jogo jogo, int jog) {
        super(jogo);
        this.jogador = jog;

    }

    @Override
    public IEstado ganhouMiniJogoCalculos(boolean ganhou) {


        if(ganhou) {
            jogo.adicionarLog("O Jogador '"+jogo.getNome(jogador)+"º ganhou a peça especial!");
            jogo.ganharPecaEspecial(jogador); // jogador ganha peça especial
            if (jogador == 0)
                return new EsperaJogadorA(jogo); // Se ganhou, retorna o mesmo jogador
            if (jogador == 1)
                return new EsperaJogadorB(jogo); // Se ganhou, retorna o mesmo jogador
        } else {
            if (jogador == 0){
                jogo.incJogadasA();
                //verificar proxima jogada do outro jogador
                if(jogo.getMogoJogo() == 1 || jogo.getMogoJogo() == 2) {
                    if (jogo.getJogadasB() + 1 == 5) {
                        return new DecisaoMiniJogo(jogo, 1);
                    }
                }
                return new EsperaJogadorB(jogo); // Se perdeu, retorna o outro jogador
            }
        }
        jogo.incJogadasB();

        //verificar proxima jogada do outro jogador
        if(jogo.getMogoJogo() == 1 || jogo.getMogoJogo() == 2) {
            if (jogo.getJogadasA() + 1 == 5) {
                return new DecisaoMiniJogo(jogo, 0);
            }
        }

        return new EsperaJogadorA(jogo); // Se perdeu, retorna o outro jogador

    }

    @Override
    public Situacao getSituacao() {
        return Situacao.MiniJogoCalculos;
    }

}
