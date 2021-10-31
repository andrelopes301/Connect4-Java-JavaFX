package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class EsperaJogadorA extends EstadoAdapter {


    public EsperaJogadorA(Jogo jogo) {
        super(jogo);
        jogo.setJogador(0);
        jogo.incTotalJogadas();
        jogo.incJogadasA();

    }

    //para nao incrementar jogadas qnd carrega um jogo
    public EsperaJogadorA(Jogo jogo, int jogoGravado) {
        super(jogo);
        jogo.setJogador(0);
    }



    @Override
    public IEstado escolherColuna(int coluna) {


        jogo.adicionarLog("O Jogador '"+jogo.getNome(0)+"' jogou a peça 'X' na coluna "+coluna+"");
        int fimJogo;
        //Escolher a coluna no tabuleiro
        jogo.escolherColuna(coluna, jogo.getPeca(0));

        fimJogo = jogo.verificaFimJogo(jogo.getPeca(0));
        //Situacao de fim de jogo
        if (fimJogo == 1){
            jogo.adicionarLog("O Jogador '"+jogo.getNome(0)+"' venceu a partida!");
            jogo.setVencedor(1); // vencedor -> Jogador A
            return new FimJogo(jogo);
        }else  if (fimJogo == 2){
            jogo.adicionarLog("Houve um empate na partida!");
            jogo.setVencedor(0); // caso de empate
            return new FimJogo(jogo);
        }

        //verificar proxima jogada do outro jogador
        if(jogo.getMogoJogo() == 1) {
            if (jogo.getJogadasB() + 1 == 5) {
                return new DecisaoMiniJogo(jogo, 1);
            }
        }

        return new EsperaJogadorB(jogo);
    }



    @Override
    public IEstado jogarPecaEspecial(int coluna, int jogador) {


        if(jogo.getPecaEspecial(jogador) > 0) {
            jogo.adicionarLog("O Jogador '"+jogo.getNome(0)+" jogou a peça especial na coluna "+coluna);
            jogo.jogarPecaEspecial(coluna, jogador);

            //verificar proxima jogada do outro jogador
            if(jogo.getMogoJogo() == 1) {
                if (jogo.getJogadasB() + 1 == 5) {
                    return new DecisaoMiniJogo(jogo, 1);
                }
            }

            return new EsperaJogadorB(jogo);
        }

        return this;
    }

    @Override
    public IEstado replayJogada() {

        int fimJogo;
        fimJogo = jogo.verificaFimJogo(jogo.getPeca(0));
        if (fimJogo == 1){
            jogo.setVencedor(1); // vencedor -> Jogador A
            return new FimJogo(jogo);
        }else  if (fimJogo == 2){
            jogo.setVencedor(0); // caso de empate
            return new FimJogo(jogo);
        }
        return new EsperaJogadorB(jogo,1);
    }


    @Override
    public IEstado voltarAtras() {
        return new EsperaJogadorB(jogo);
    }

    @Override
    public IEstado terminarJogo() {
            return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.EsperaJogadorA;
    }



}