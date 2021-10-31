package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

public class Inicio extends EstadoAdapter{

    public Inicio(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado iniciarJogo() {
        jogo.adicionarLog("O jogo vai começar!");
        jogo.iniciar();
        return new EsperaModoJogo(jogo);
    }


    @Override
    public Situacao getSituacao() {
        return Situacao.Inicio;
    }
}
