package jogo.iu.gui.estados;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import java.io.File;

import static jogo.Constantes.JOGO;


public class InicioPane extends BorderPane {
    private JogoObservavel jogoObservavel;

    VBox menuInicial;
    Button btnIniciarJogo, btnCarregarJogo, btnReplays, btnSairJogo;


    public InicioPane(JogoObservavel jogoObservavel) {
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


    private void criarVista(){

        btnIniciarJogo = new Button("Novo Jogo");
        btnCarregarJogo = new Button("Carregar Jogo");
        btnReplays = new Button("Replays Anteriores");
        btnSairJogo = new Button("Sair do Jogo");

        menuInicial = new VBox(20);
        menuInicial.getChildren().addAll(btnIniciarJogo,btnCarregarJogo,btnReplays,btnSairJogo);
        menuInicial.setAlignment(Pos.CENTER);
        setCenter(menuInicial);

    }

    private void registarListeners(){

        btnIniciarJogo.setOnAction((e)->jogoObservavel.iniciarJogo());

        btnCarregarJogo.setOnAction((ActionEvent e)-> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Binary files (*.bin)", "*.bin");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialDirectory(new File("./savegames"));
            File nomeFich = fileChooser.showOpenDialog(null);
            if (nomeFich != null) {
                jogoObservavel.carregarJogoFX(nomeFich);
            } else {
                System.err.println("Leitura cancelada ");
            }
        });

        btnReplays.setOnAction((ActionEvent e)-> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Binary files (*.bin)", "*.bin");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialDirectory(new File("./replays"));
            File nomeFich = fileChooser.showOpenDialog(null);
            if (nomeFich != null) {
                jogoObservavel.setReplayCarregado();
                jogoObservavel.carregarReplayFX(nomeFich);
            } else {
                System.err.println("Leitura cancelada ");
            }

        });

        btnSairJogo.setOnAction((ActionEvent e)-> {
            Stage janela = (Stage) this.getScene().getWindow();
            fireEvent( new WindowEvent(janela, WindowEvent.WINDOW_CLOSE_REQUEST));
        });


    }


    private void atualiza() {

        this.setVisible(jogoObservavel.getSituacaoAtual() ==  Situacao.Inicio );
    }

}