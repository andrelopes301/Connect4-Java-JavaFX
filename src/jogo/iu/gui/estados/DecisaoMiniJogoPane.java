package jogo.iu.gui.estados;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import static jogo.Constantes.JOGO;

public class DecisaoMiniJogoPane extends BorderPane {
    private JogoObservavel jogoObservavel;

    VBox menu;
    Button btnSim, btnNao;
    Text jogador;

    public DecisaoMiniJogoPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criarVista();
        registarListeners();
        registarObservador();
        atualiza();
    }

    private void registarObservador() {
        // regista um observador do jogoObservavel
        jogoObservavel.addPropertyChangeListener(JOGO, evt -> {
            atualiza();
        });
    }


    private void criarVista() {


        jogador = new Text();
        jogador.setTextAlignment(TextAlignment.CENTER);  jogador.setFill(Color.WHITE);
        jogador.setStyle("-fx-font: 20 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");


        btnSim = new Button("Sim");
        btnNao = new Button("Nao");

        menu = new VBox(10);
        menu.getChildren().addAll(jogador,btnSim,btnNao);
        menu.setSpacing(10);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        setCenter(menu);
    }


    private void registarListeners() {


        btnSim.setOnAction((ActionEvent e) -> {
            jogoObservavel.decisao(1);
        });


        btnNao.setOnAction((ActionEvent e) -> {
            jogoObservavel.decisao(0);
        });

    }


    private void atualiza() {

        if(jogoObservavel.getSituacaoAtual() == Situacao.DecisaoMiniJogo) {
            if(jogoObservavel.getJogadorJogar() == 0)
              jogador.setText("Decisão do Jogador '" + jogoObservavel.getNome(1) + "'\n\nDeseja realizar um Minijogo?\n");
            else
                jogador.setText("Decisão do Jogador '" + jogoObservavel.getNome(0) + "'\n\nDeseja realizar um Minijogo?\n");
        }

        this.setVisible(jogoObservavel.getSituacaoAtual() == Situacao.DecisaoMiniJogo);
    }

}