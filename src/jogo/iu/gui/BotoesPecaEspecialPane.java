package jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import jogo.logica.JogoObservavel;

import static jogo.Constantes.JOGO;

public class BotoesPecaEspecialPane extends HBox {

    private JogoObservavel jogoObservavel;
    Button col_1, col_2, col_3, col_4, col_5, col_6, col_7;
    int jogador;

    public BotoesPecaEspecialPane(JogoObservavel jogoObservavel, int joga) {

        this.jogoObservavel = jogoObservavel;
        this.jogador = joga;
        this.criarVista();
        registarListeners();
        registarObservador();

    }

    private void registarObservador() {
        // regista um observador do jogoObservavel
        jogoObservavel.addPropertyChangeListener(JOGO, evt -> {
            atualiza();
        });
    }


    class BotaoColuna extends Button {

        public BotaoColuna(String s) {

            setText(s);
            setId("botaoColuna");

        }

    }

    private void criarVista() {

        col_1 = new BotaoColuna("1");
        col_2 = new BotaoColuna("2");
        col_3 = new BotaoColuna("3");
        col_4 = new BotaoColuna("4");
        col_5 = new BotaoColuna("5");
        col_6 = new BotaoColuna("6");
        col_7 = new BotaoColuna("7");


        setSpacing(10);
        setPadding(new Insets(20, 0, 0, 36));
        getChildren().addAll(col_1, col_2, col_3, col_4, col_5, col_6, col_7);

    }


    private void registarListeners() {


        col_1.setOnMouseClicked(e -> {
           jogoObservavel.jogarPecaEspecial(1,jogador);
            setVisible(false);
        });

        col_2.setOnMouseClicked(e -> {
            jogoObservavel.jogarPecaEspecial(2,jogador);
            setVisible(false);
        });

        col_3.setOnMouseClicked(e -> {
            jogoObservavel.jogarPecaEspecial(3,jogador);
            setVisible(false);
        });

        col_4.setOnMouseClicked(e -> {
            jogoObservavel.jogarPecaEspecial(4,jogador);
            setVisible(false);
        });

        col_5.setOnMouseClicked(e -> {
            jogoObservavel.jogarPecaEspecial(5,jogador);
            setVisible(false);
        });

        col_6.setOnMouseClicked(e -> {
            jogoObservavel.jogarPecaEspecial(6,jogador);
            setVisible(false);
        });
        col_7.setOnMouseClicked(e -> {
            jogoObservavel.jogarPecaEspecial(7,jogador);
            setVisible(false);
        });

    }

    private void atualiza() {

        if(jogoObservavel.verificaColunaVazia(1)){
            col_1.setVisible(false);
        }else
            col_1.setVisible(true);

        if(jogoObservavel.verificaColunaVazia(2)){
            col_2.setVisible(false);
        }else
            col_2.setVisible(true);

        if(jogoObservavel.verificaColunaVazia(3)){
            col_3.setVisible(false);
        }else
            col_3.setVisible(true);

        if(jogoObservavel.verificaColunaVazia(4)){
            col_4.setVisible(false);
        }else
            col_4.setVisible(true);

        if(jogoObservavel.verificaColunaVazia(5)){
            col_5.setVisible(false);
        }else
            col_5.setVisible(true);

        if(jogoObservavel.verificaColunaVazia(6)){
            col_6.setVisible(false);
        }else
            col_6.setVisible(true);

        if(jogoObservavel.verificaColunaVazia(7)){
            col_7.setVisible(false);
        }else
            col_7.setVisible(true);




    }
}
