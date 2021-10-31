package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class FimJogo extends EstadoAdapter{

    public FimJogo(Jogo jogo){
        super(jogo);
    }

    @Override
    public IEstado terminarJogo() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.FimJogo;
    }

}
