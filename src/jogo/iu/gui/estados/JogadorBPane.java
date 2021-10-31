package jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import static jogo.Constantes.JOGO;

public class JogadorBPane extends BorderPane {
    private JogoObservavel jogoObservavel;


    Stage pedirNumVoltaAtras;
    VBox menuJogador;
    HBox voltar, caixaOpcao, botoesColuna, botoesPecaEspecial;
    Button btnEscolherColuna, btnVoltarAtras, btnPecaEspecial, btnVoltarMenuPrincipal, btnVerPecaJogada, btnVerPecaJogadaReplay;
    Button btnContinuar,btnSair;
    Alert alert;
    TextField numVoltasAtrasField;
    Text jogada, creditos;

    public JogadorBPane(JogoObservavel jogoObservavel,HBox botoes, HBox botoesEspecial) {

        this.jogoObservavel = jogoObservavel;
        this.botoesColuna = botoes;
        this.botoesPecaEspecial = botoesEspecial;
        criarVista();
        registarObservador();
        registarListeners();
        atualiza();

    }

    private void registarObservador() {
        // regista um observador do jogoObservavel
        jogoObservavel.addPropertyChangeListener(JOGO, evt -> {
            atualiza();
        });
    }


    private void criarVista() {

        Label jogadorB = new Label("JOGADOR B");
        jogadorB.setTextFill(Color.WHITE);
        jogadorB.setId("t");


        btnEscolherColuna = new Button("Jogar Peça");
        btnVoltarAtras = new Button("Voltar Atrás");
        btnPecaEspecial= new Button("Jogar Peça Especial");
        btnVoltarMenuPrincipal= new Button("Menu Principal");
        btnVerPecaJogada = new Button("Ver Peça Jogada");
        btnVerPecaJogadaReplay = new Button("Ver Peça Jogada");

        menuJogador = new VBox(20);
        menuJogador.getChildren().addAll(jogadorB,btnEscolherColuna, btnVoltarAtras, btnPecaEspecial);
   //     menuJogador.setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID,
    //            null, new BorderWidths(5))));

        menuJogador.setAlignment(Pos.CENTER);

        voltar = new HBox(20);
        voltar.getChildren().add(btnVoltarMenuPrincipal);
        voltar.setAlignment(Pos.CENTER);
        setCenter(menuJogador);
        setBottom(voltar);



        alert = new Alert(Alert.AlertType.NONE);
        criarPoPupVista();

    }

    private void criarPoPupVista(){


        BorderPane caixa = new BorderPane();
        caixa.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                CornerRadii.EMPTY, Insets.EMPTY)));
        pedirNumVoltaAtras = new Stage();
        pedirNumVoltaAtras.setTitle("Voltar Atrás");


        //Botoes--------------------------------------------------------
        HBox botoes = new HBox();
        btnContinuar = new Button("Continuar");
        btnSair = new Button("Sair");

        botoes.setAlignment(Pos.CENTER);
        botoes.setSpacing(10);
        botoes.setPadding(new Insets(10));

        //Texto--------------------------------------------------------
        HBox caixatexto = new HBox();
        botoes.getChildren().addAll(btnContinuar,btnSair);
        Text text = new Text("Introduza o numero de jogadas a voltar atrás:");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font: 14 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        caixatexto.setPadding(new Insets(10,0,0,0));
        caixatexto.getChildren().add(text);
        caixatexto.setAlignment(Pos.CENTER);

        //Caixa escolha do numero--------------------------------------------------------

        caixaOpcao = new HBox();
        jogada = new Text();
        creditos = new Text();
        numVoltasAtrasField = new TextField("");
        numVoltasAtrasField.setPrefWidth(50);
        caixaOpcao.setAlignment(Pos.CENTER);
        caixaOpcao.getChildren().addAll(jogada,numVoltasAtrasField,creditos);
        caixaOpcao.setSpacing(10);
        caixaOpcao.setPadding(new Insets(10,0,0,0));
        //----------------------------------------------------------------------

        caixa.setTop(caixatexto);
        caixa.setCenter(caixaOpcao);
        caixa.setBottom(botoes);
        Scene scene = new Scene(caixa,400,150);
        pedirNumVoltaAtras.setResizable(false);
        pedirNumVoltaAtras.setScene(scene);
        pedirNumVoltaAtras.initModality(Modality.APPLICATION_MODAL);
        pedirNumVoltaAtras.setAlwaysOnTop(true);

    }


    private void registarListeners() {


        btnEscolherColuna.setOnMouseClicked(e -> {

            if (botoesPecaEspecial.isVisible())
                botoesPecaEspecial.setVisible(false);


            if(botoesColuna.isVisible())
                botoesColuna.setVisible(false);
            else
                botoesColuna.setVisible(true);
        });



        btnVerPecaJogada.setOnAction(e -> {
            int coluna;
            do {
                coluna = jogoObservavel.gerarColuna();
                System.out.println("Coluna jogada:" + coluna);
            } while (jogoObservavel.getEstadoColuna(coluna));

            jogoObservavel.escolherColuna(coluna);
        });

        btnVerPecaJogadaReplay.setOnAction(e -> {
            jogoObservavel.redo();
            jogoObservavel.replayJogada();

        });

        btnVoltarAtras.setOnAction(e -> {

            if (jogoObservavel.getJogadas() - 1 == 0 ){
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Ocorreu um problema!");
                alert.setContentText("Não é possível voltar atrás na jogada inicial!");
                alert.show();
          //      System.out.println("\n[Info] Não é possível voltar atrás na jogada inicial!");
                return;
            }
            if (jogoObservavel.getCreditosJogador(1) == 0){
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Ocorreu um problema!");
                alert.setContentText("Não tem créditos suficientes para voltar atrás!");
                alert.show();
              //  System.out.println("\n[Info] Não tem créditos suficientes para voltar atrás!");
                return;
            }


            if(pedirNumVoltaAtras.isShowing()) {
                pedirNumVoltaAtras.close();
            }
            else{

                pedirNumVoltaAtras.show();
            }
        });


        btnPecaEspecial.setOnAction(e -> {

            if (jogoObservavel.getPecaEsp(1) == 0){
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Ocorreu um problema!");
                alert.setContentText("Não tem peças especiais!");
                alert.show();
               // System.out.println("\n[Info] Não tem Peças Especiais!");
                return;
            }

            if (botoesColuna.isVisible())
                botoesColuna.setVisible(false);


            if(botoesPecaEspecial.isVisible())
                botoesPecaEspecial.setVisible(false);
            else
                botoesPecaEspecial.setVisible(true);

        });

        btnVoltarMenuPrincipal.setOnAction(e -> {
            botoesColuna.setVisible(false);
            jogoObservavel.clearTabuleiro();
            jogoObservavel.terminarJogo();
        });

    }




    private void atualiza() {


        if( jogoObservavel.getModoJogo() == 4){

            menuJogador = new VBox(20);
            menuJogador.getChildren().add(btnVerPecaJogadaReplay);
            menuJogador.setAlignment(Pos.CENTER);

            voltar = new HBox(20);
            voltar.getChildren().add(btnVoltarMenuPrincipal);
            voltar.setAlignment(Pos.CENTER);
            setCenter(menuJogador);
            setBottom(voltar);

        }
        else if(jogoObservavel.getModoJogo() == 2 || jogoObservavel.getModoJogo() == 3){
            menuJogador = new VBox(20);
            menuJogador.getChildren().add(btnVerPecaJogada);

            menuJogador.setAlignment(Pos.CENTER);

            voltar = new HBox(20);
            voltar.getChildren().add(btnVoltarMenuPrincipal);
            voltar.setAlignment(Pos.CENTER);
            setCenter(menuJogador);
            setBottom(voltar);
        }
        else {
            menuJogador = new VBox(20);
            menuJogador.getChildren().addAll(btnEscolherColuna, btnVoltarAtras, btnPecaEspecial);
            menuJogador.setAlignment(Pos.CENTER);


            if (jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorB) {
                jogada.setText("Jogada: " + jogoObservavel.getJogadas());
                creditos.setText("Creditos: " + jogoObservavel.getCreditosJogador(1));
            }


            //BtnContinuar-------------------------------------------------------------------------------

            btnContinuar.setOnMouseClicked(e -> {

                int numVoltaAtras;

                try {
                    do {
                        numVoltaAtras = getNumVoltasAtras();
                        if (numVoltaAtras < 1 || numVoltaAtras > 5 || numVoltaAtras > jogoObservavel.getJogadas() || numVoltaAtras > jogoObservavel.getCreditosJogador(1)){
                            alert.setAlertType(Alert.AlertType.WARNING);
                            alert.setTitle("Aviso");
                            alert.setHeaderText("Ocorreu um problema!");
                            alert.setContentText("Não é possível voltar atrás!");
                            pedirNumVoltaAtras.setAlwaysOnTop(false);
                            alert.show();
                        //    System.out.println("\n[Info] Não é possível voltar atrás!");
                            return;
                        }
                    } while (numVoltaAtras > 5 || numVoltaAtras > jogoObservavel.getJogadas() || numVoltaAtras > jogoObservavel.getCreditosJogador(1));
                } catch (NumberFormatException ex){
                    return;
                }

               // System.out.println("num voltas atras jogador B: "+numVoltaAtras);

                pedirNumVoltaAtras.close();
                jogoObservavel.voltarAtras(1, numVoltaAtras);
            });

            //BtnSair-------------------------------------------------------------------------------

            btnSair.setOnMouseClicked(e -> {
                pedirNumVoltaAtras.close();
            });


            voltar = new HBox(20);
            voltar.getChildren().add(btnVoltarMenuPrincipal);
            voltar.setAlignment(Pos.CENTER);
            setCenter(menuJogador);
            setBottom(voltar);

        }

        this.setVisible(jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorB);
    }

    private int getNumVoltasAtras() throws NumberFormatException {

        String s1 = (numVoltasAtrasField.getText()).trim();

        if (s1.length() < 1){
            throw new NumberFormatException();
        }
        int numVoltaAtras = Integer.parseInt(s1);
        return numVoltaAtras;

    }

}