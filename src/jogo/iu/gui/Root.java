package jogo.iu.gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jogo.iu.gui.PanePrincipal;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import java.io.File;

import static jogo.Constantes.JOGO;


public class Root extends BorderPane {

    private JogoObservavel jogoObservavel;
    private MenuItem novoJogo, carregarJogo, gravarJogo, sairJogo;
    private PanePrincipal panePrincipal;

    public Root(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        criarVista();
        menus();
        registarObservador();
        atualiza();
    }

    private void registarObservador(){
        // regista um observador do jogoObservavel
        jogoObservavel.addPropertyChangeListener(JOGO, evt -> {
            atualiza();
        });
    }



    private void criarVista(){

        //Definir cor do Background
        setStyle("-fx-background-color: linear-gradient(#4b6cb7,#182848);");


        panePrincipal = new PanePrincipal(jogoObservavel);
        setCenter(panePrincipal);
    }


    private void menus() {
        MenuBar menuBar = new MenuBar();
        setTop(menuBar);

        // menu Jogo
        Menu jogoMenu = new Menu("_Jogo");  // underscore: abre com alt + j

        novoJogo = new MenuItem("Novo Jogo");
        novoJogo.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        carregarJogo = new MenuItem("Carregar Jogo");
        carregarJogo.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        gravarJogo = new MenuItem("Gravar Jogo");
        gravarJogo.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));

        sairJogo = new MenuItem("Sair");
        sairJogo.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        jogoMenu.getItems().addAll( novoJogo, carregarJogo, gravarJogo,new SeparatorMenuItem(), sairJogo);

        novoJogo.setOnAction((ActionEvent e)-> {
            if(jogoObservavel.getTabuleiro() != null) {
                jogoObservavel.clearTabuleiro();
                jogoObservavel.terminarJogo();
            }
        });


        carregarJogo.setOnAction(new CarregarObjMenuBarListener());

        gravarJogo.setOnAction(new GravarObjMenuBarListener());

        sairJogo.setOnAction((ActionEvent e)-> {
            Stage janela2 = (Stage) this.getScene().getWindow();
            fireEvent( new WindowEvent(janela2, WindowEvent.WINDOW_CLOSE_REQUEST));
        });



        // menu ajuda
        Menu ajudaMenu = new Menu("_Ajuda");

        MenuItem comoJogarMI = new MenuItem("Como jogar");
        comoJogarMI.setAccelerator(new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN));

        MenuItem acercaMI = new MenuItem("Acerca");
        acercaMI.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        ajudaMenu.getItems().addAll(comoJogarMI, acercaMI);

        comoJogarMI.setOnAction(new AjudaListener());
        acercaMI.setOnAction(new AcercaListener());


        menuBar.getMenus().addAll(jogoMenu,ajudaMenu);
    }


    class CarregarObjMenuBarListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
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
        }
    }

    class GravarObjMenuBarListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./savegames"));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Binary files (*.bin)", "*.bin");
            fileChooser.getExtensionFilters().add(extFilter);
            File nomeFich = fileChooser.showSaveDialog(null);
            if (nomeFich != null) {
                jogoObservavel.gravarJogoFX(nomeFich);
            } else {
                System.err.println("Gravacao cancelada ");
            }
        }
    }



    class AjudaListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            Alert dialogoResultado = new Alert(Alert.AlertType.INFORMATION);
            dialogoResultado.setHeaderText("Ajuda");
            dialogoResultado.setContentText("Ganha o jogador que conseguir colocar 4 peças seguidas em linha, na vertical, horizontal ou diagonal");
            dialogoResultado.showAndWait();
        }
    }

    class AcercaListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            Alert dialogoResultado = new Alert(Alert.AlertType.INFORMATION);
            dialogoResultado.setHeaderText("Acerca");
            dialogoResultado.setContentText("Trabalho realizado por: \n\nAndré Lopes - 2019139754");
            dialogoResultado.showAndWait();
        }
    }

    private void atualiza() {
        novoJogo.setDisable(((jogoObservavel.getSituacaoAtual() == Situacao.Inicio )));
        gravarJogo.setDisable(!(jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorA || jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorB  ));



    }

}
