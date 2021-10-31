package jogo.iu.gui.estados;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;
import jogo.logica.estados.EsperaNome;

import static jogo.Constantes.JOGO;


public class ModoJogoPane  extends BorderPane {
    private JogoObservavel jogoObservavel;

    VBox menuModoJogo;
    Button btnHumvsHum, btnHumvsComp, btnCompvsComp;

    public ModoJogoPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criarVista();
        registarListeners();
        registarObservador();
        atualiza();


    }

    private void registarObservador() {
        // regista um observador do jogoObservavel
        jogoObservavel.addPropertyChangeListener(JOGO, evt -> {
         //   jogoObservavel.getModoJogo();
            atualiza();
        });
    }


    private void criarVista(){

        btnHumvsHum = new Button("Hum vs Hum");
        btnHumvsComp = new Button("Hum vs PC");
        btnCompvsComp = new Button("PC vs PC");


        menuModoJogo = new VBox(20);
        menuModoJogo.getChildren().addAll(btnHumvsHum,btnHumvsComp,btnCompvsComp);
        menuModoJogo.setAlignment(Pos.CENTER);

        setCenter(menuModoJogo);

    }

    private void registarListeners(){


        btnHumvsHum.setOnAction((e)->jogoObservavel.escolherNomes(1));
        btnHumvsComp.setOnAction((e)->jogoObservavel.escolherNomes(2));

        btnCompvsComp.setOnAction(e -> {
            jogoObservavel.escolherNomes(3);
            jogoObservavel.sortearJogador(3,"VirtualA","VirtualB");

        });



    }

    private void atualiza() {

        this.setVisible(jogoObservavel.getSituacaoAtual() ==  Situacao.EsperaModoJogo );
    }

}