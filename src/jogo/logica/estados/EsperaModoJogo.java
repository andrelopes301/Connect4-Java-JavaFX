package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

    public class EsperaModoJogo extends EstadoAdapter {

        public EsperaModoJogo(Jogo jogo) {
            super(jogo);
        }


        @Override
        public IEstado escolherNomes(int modoJogo) {

           jogo.setModoJogo(modoJogo);

            if(modoJogo == 1)
                jogo.adicionarLog("O modo de jogo escolhido foi: Jogador A(Humano)  VS  Jogador B(Humano)");
            else if (modoJogo == 2)
                jogo.adicionarLog("O modo de jogo escolhido foi: Jogador A(Humano)  VS  Jogador B(Virtual)");
            else if (modoJogo == 3)
                jogo.adicionarLog("O modo de jogo escolhido foi: Jogador A(Virtual) VS  Jogador B(Virtual)");

            return new EsperaNome(jogo);
        }

        @Override
        public IEstado terminarJogo() {
            return new Inicio(jogo);
        }

        @Override
        public Situacao getSituacao() {
            return Situacao.EsperaModoJogo;
        }

    }


