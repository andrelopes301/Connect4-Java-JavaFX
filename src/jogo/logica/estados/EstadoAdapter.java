package jogo.logica.estados;

import jogo.logica.dados.Jogo;

public abstract class EstadoAdapter implements IEstado{

    protected Jogo jogo;

    protected EstadoAdapter(Jogo jogo){
        this.jogo = jogo;
    }

    @Override
    public IEstado iniciarJogo(){ return this;}
    @Override
    public IEstado escolherNomes(int modoJogo){ return this;}
    @Override
    public IEstado sortearJogador(int modoJogo, String jogadorA, String jogadorB) { return this;}
    @Override
    public IEstado escolherColuna(int coluna) {
        return this;
    }
    @Override
    public IEstado decisao(int opcao) {
        return this;
    }
    @Override
    public IEstado ganhouMiniJogoCalculos(boolean ganhou) {return this;}
    @Override
    public IEstado ganhouMiniJogoPalavras(boolean ganhou) {return this;}
    @Override
    public IEstado jogarPecaEspecial(int coluna,int jogador) {
        return this;
    }
    @Override
    public IEstado  replayJogada() {
        return this;
    }
    @Override
    public IEstado voltarAtras() {
        return this;
    }
    @Override
    public IEstado terminarJogo() {
        return this;
    }

}
