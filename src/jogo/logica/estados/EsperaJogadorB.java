package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;


public class EsperaJogadorB extends EstadoAdapter {

    public EsperaJogadorB(Jogo jogo) {
        super(jogo);
        jogo.setJogador(1);
        jogo.incTotalJogadas();
        jogo.incJogadasB();
    }

    //para nao incrementar jogadas qnd carrega um jogo
    public EsperaJogadorB(Jogo jogo, int jogoGravado) {

        super(jogo);
        jogo.setJogador(1);
    }


    @Override
    public IEstado escolherColuna(int coluna) {


        jogo.adicionarLog("O Jogador '"+jogo.getNome(1)+"' jogou a peça 'X' na coluna "+coluna+"");
        int fimJogo;
        jogo.escolherColuna(coluna, jogo.getPeca(1));


        fimJogo = jogo.verificaFimJogo(jogo.getPeca(1));
        if (fimJogo == 1){
            jogo.adicionarLog("O Jogador '"+jogo.getNome(1)+"' venceu a partida!");
            jogo.setVencedor(2); // vencedor -> Jogador A
            return new FimJogo(jogo);
        }else  if (fimJogo == 2){
            jogo.adicionarLog("Houve um empate na partida!");
            jogo.setVencedor(0); // caso de empate
            return new FimJogo(jogo);
        }
        //verificar proxima jogada do outro jogador
        if(jogo.getMogoJogo() == 1 || jogo.getMogoJogo() == 2) {
            if (jogo.getJogadasA() + 1 == 5) {
                return new DecisaoMiniJogo(jogo, 0);
            }
        }


        return new EsperaJogadorA(jogo);
    }


    @Override
    public IEstado jogarPecaEspecial(int coluna, int jogador) {



       if(jogo.getPecaEspecial(jogador) > 0) {
           jogo.adicionarLog("O Jogador '"+jogo.getNome(1)+" jogou a peça especial na coluna "+coluna);
           jogo.jogarPecaEspecial(coluna, jogador);

           //verificar proxima jogada do outro jogador
           if(jogo.getMogoJogo() == 1 || jogo.getMogoJogo() == 2) {
               if (jogo.getJogadasA() + 1 == 5) {
                   return new DecisaoMiniJogo(jogo, 0);
               }
           }

           return new EsperaJogadorA(jogo);
       }
       return this;
    }


    @Override
    public IEstado replayJogada() {

        int fimJogo;
        fimJogo = jogo.verificaFimJogo(jogo.getPeca(1));
        if (fimJogo == 1){
            jogo.setVencedor(2); // vencedor -> Jogador B
            return new FimJogo(jogo);
        }else  if (fimJogo == 2){
            jogo.setVencedor(0); // caso de empate
            return new FimJogo(jogo);
        }

        return new EsperaJogadorA(jogo,1);
    }


    @Override
    public IEstado voltarAtras() {
        return new EsperaJogadorA(jogo);
    }


    public IEstado terminarJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.EsperaJogadorB;
    }
}