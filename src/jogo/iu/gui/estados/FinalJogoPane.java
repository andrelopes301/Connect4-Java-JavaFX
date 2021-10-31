package jogo.iu.gui.estados;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import static jogo.Constantes.JOGO;

public class FinalJogoPane extends BorderPane {
    private JogoObservavel jogoObservavel;

    VBox menuInicial;
    Button btnVoltarMenuPrincipal, btnSairJogo;
    Alert vencedor;

    public FinalJogoPane(JogoObservavel jogoObservavel) {
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


        vencedor = new Alert(Alert.AlertType.INFORMATION);
        vencedor.setTitle("Final de Jogo");
        vencedor.setHeaderText("A partida chegou ao fim!");



        btnVoltarMenuPrincipal = new Button("Menu Principal");
        btnSairJogo = new Button("Sair do Jogo");

        menuInicial = new VBox(20);
        menuInicial.getChildren().addAll(btnVoltarMenuPrincipal, btnSairJogo);

        menuInicial.setAlignment(Pos.CENTER);
        setCenter(menuInicial);

    }

    private void registarListeners() {

        btnVoltarMenuPrincipal.setOnAction(e -> {
            jogoObservavel.clearTabuleiro();
            jogoObservavel.terminarJogo();

        });

        btnSairJogo.setOnAction((ActionEvent e) -> {
            Stage janela = (Stage) this.getScene().getWindow();
            fireEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSE_REQUEST));
        });

    }


    private void atualiza() {


        if(jogoObservavel.getSituacaoAtual() == Situacao.FimJogo) {

            if(jogoObservavel.getVencedor() == 1)
                  vencedor.setContentText("O jogador "+jogoObservavel.getNome(0)+" foi o vencedor da partida!");
            else if(jogoObservavel.getVencedor() == 2)
                vencedor.setContentText("O jogador "+jogoObservavel.getNome(1)+" foi o vencedor da partida!");
            else
                vencedor.setContentText("Houve um empate na partida!");


            vencedor.show();

            int cont = jogoObservavel.getTamanhoPasta("replays");

            if (jogoObservavel.getModoJogo() != 4) {
                jogoObservavel.gravarReplay("replay" + (cont + 1));
                System.out.println("[INFO] Replay do jogo gravado com sucesso!  [replay" + (cont + 1) + "]\n");

            }
        }


        this.setVisible(jogoObservavel.getSituacaoAtual() == Situacao.FimJogo);
    }

}