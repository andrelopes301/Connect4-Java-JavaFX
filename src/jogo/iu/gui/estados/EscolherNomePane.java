package jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import static jogo.Constantes.JOGO;


public class EscolherNomePane extends BorderPane {
    private JogoObservavel jogoObservavel;
    VBox caixaModoJogo;
    Button btnComecarJogo, btnVoltarMenuPrincipal;
    String nomeJogadorA, nomeJogadorB;
    Text texto;
    TextField jogadorA, jogadorB;
    int modoJogo;


    public EscolherNomePane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criarVistaeAtualizar();
        registarObservador();


    }

    private void registarObservador() {
        jogoObservavel.addPropertyChangeListener(JOGO, evt -> {
            criarVistaeAtualizar();
        });
    }



    private void criarVistaeAtualizar() {


        btnComecarJogo = new Button("Comecar Jogo");
        btnVoltarMenuPrincipal = new Button("Menu Principal");
        caixaModoJogo = new VBox();
        caixaModoJogo.setAlignment(Pos.CENTER);

        texto = new Text("Introduza o nome dos jogadores:");
        texto.setId("t");
        jogadorA = new TextField();
        jogadorA.setPromptText("Nome do Jogador A");
        jogadorB = new TextField();
        jogadorB.setPromptText("Nome do Jogador B");
        jogadorA.setPadding(new Insets(10));
        jogadorB.setPadding(new Insets(10));
        jogadorA.setFocusTraversable(false);
        jogadorB.setFocusTraversable(false);

        jogadorA.setMaxWidth(200);
        jogadorB.setMaxWidth(200);
        caixaModoJogo.setSpacing(20);


        Alert alert = new Alert(Alert.AlertType.NONE);

        modoJogo = jogoObservavel.getModoJogo();

        if (modoJogo == 1) {
            caixaModoJogo.getChildren().addAll(texto, jogadorA, jogadorB, btnComecarJogo);
            setCenter(caixaModoJogo);

            btnComecarJogo.setOnAction(e -> {
                nomeJogadorA = jogadorA.getText();
                nomeJogadorB = jogadorB.getText();

                if (!nomeJogadorA.equals(nomeJogadorB) && !nomeJogadorA.equals("") && !nomeJogadorB.equals(""))
                    jogoObservavel.sortearJogador(jogoObservavel.getModoJogo(), nomeJogadorA, nomeJogadorB);
                else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText("Ocorreu um problema!");
                    alert.setContentText("Os nomes dos jogadores A e B devem ser diferentes, e nao devem estar vazios!");
                    alert.show();
                }

            });

        } else if (modoJogo == 2) {
            caixaModoJogo.getChildren().addAll(texto, jogadorA, btnComecarJogo);
            setCenter(caixaModoJogo);

            btnComecarJogo.setOnAction(e -> {
                nomeJogadorA = jogadorA.getText();
                nomeJogadorB = "Virtual";

                if (!nomeJogadorA.equals(""))
                    jogoObservavel.sortearJogador(jogoObservavel.getModoJogo(), nomeJogadorA, nomeJogadorB);
                else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText("Ocorreu um problema!");
                    alert.setContentText("O nome do jogador A nao pode estar vazio!");
                    alert.show();
                }
            });
        } else if (modoJogo == 3){
            nomeJogadorA = "VirtualA";
            nomeJogadorB = "VirtualB";
        }


        this.setVisible(jogoObservavel.getSituacaoAtual() == Situacao.EsperaNome);
    }



}
