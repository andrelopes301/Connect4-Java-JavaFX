package jogo.iu.gui.estados;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import static jogo.Constantes.JOGO;

public class MiniJogoPalavrasPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    Button btnComecar, btnContinuar;
    VBox caixaCentral;
    Text text, descricao;
    TextField respostaField;
    Label frase, infoGanhou;
    String palavras, resposta;
    HBox caixatexto;
    long inicioContagem, tempoDemorado;
    int tempoMaximo;

    public MiniJogoPalavrasPane(JogoObservavel jogoObservavel) {


        this.jogoObservavel = jogoObservavel;
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

        setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID,
                null, new BorderWidths(5))));

        setMinWidth(350);
        caixatexto = new HBox();
        btnComecar = new Button("Comecar Mini-Jogo");
        btnContinuar = new Button("Continuar");

        text = new Text("Mini-Jogo das Palavras");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 20 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");

        caixatexto.setPadding(new Insets(30));
        caixatexto.setBackground((new Background(new BackgroundFill(Color.rgb(23, 136, 212, 0.91),
                CornerRadii.EMPTY,Insets.EMPTY))));
        caixatexto.getChildren().add(text);
        caixatexto.setAlignment(Pos.CENTER);

        caixaCentral = new VBox();
        caixaCentral.setPadding(new Insets(60,0,30,0));
        caixaCentral.setBackground((new Background(new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY,Insets.EMPTY))));

        descricao = new Text("Tem um tempo limite para digitar corretamente as palavras que lhe irão aparecer!");


        descricao.setTextAlignment(TextAlignment.CENTER);
        descricao.setStyle("-fx-font: 20 'Helvetica Neue', sans-serif; -fx-font-weight: normal;");
        descricao.setWrappingWidth(200);

        infoGanhou = new Label();
        infoGanhou.setStyle("-fx-text-fill: Black;-fx-font: 16 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        infoGanhou.setMaxWidth(300);
        infoGanhou.setAlignment(Pos.CENTER);

        frase = new Label();
        frase.setStyle("-fx-text-fill: Black;-fx-font: 15 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        frase.setPadding(new Insets(0,30,0,30));
        frase.setAlignment(Pos.CENTER);

        respostaField = new TextField("");
        respostaField.setPadding(new Insets(10));
        respostaField.setMaxWidth(250);
        setAlignment(respostaField,Pos.CENTER);

        caixaCentral.setAlignment(Pos.CENTER);
        caixaCentral.setSpacing(30);
        caixaCentral.getChildren().addAll(descricao,btnComecar);



        setTop(caixatexto);
        setCenter(caixaCentral);



    }


    private void registarListeners() {



        btnComecar.setOnAction((ActionEvent e) -> {
            caixaCentral.getChildren().clear();
            System.out.println(palavras);
            inicioContagem = jogoObservavel.contagem();
            frase.setText(palavras);
            caixaCentral.getChildren().addAll(frase,respostaField);

        });



        btnContinuar.setOnAction((ActionEvent e) -> {

            caixaCentral.getChildren().clear();
            caixaCentral.getChildren().addAll(descricao,btnComecar);
            respostaField = new TextField("");
            respostaField.setPadding(new Insets(10));
            respostaField.setMaxWidth(250);
            setAlignment(respostaField,Pos.CENTER);

            if(jogoObservavel.palavrasCertas(palavras,resposta,tempoDemorado,tempoMaximo))
                jogoObservavel.ganhouMiniJogoPalavras(true);
            else
                jogoObservavel.ganhouMiniJogoPalavras(false);


        });
    }

    private void atualiza() {

        palavras = jogoObservavel.lerPalavrasFicheiro();
        tempoMaximo = jogoObservavel.TempoMaximo(palavras);

        respostaField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    tempoDemorado = jogoObservavel.tempoDemorado(inicioContagem);
                    resposta = respostaField.getText();
                    caixaCentral.getChildren().remove(respostaField);
                    frase.setText(jogoObservavel.mostraResultadoMiniJogoPalavras(palavras,resposta,tempoDemorado,tempoMaximo));

                    if(jogoObservavel.palavrasCertas(palavras,resposta,tempoDemorado,tempoMaximo)) // saber se ganhou o minijogo
                        infoGanhou.setText("Ganhou o MiniJogo e continua\na ser a sua vez de jogar!");
                    else
                         infoGanhou.setText("Perdeu o MiniJogo e perdeu\na sua vez de jogar!");


                    caixaCentral.getChildren().addAll(infoGanhou, btnContinuar);

                }
            }
        });

        this.setVisible(jogoObservavel.getSituacaoAtual() == Situacao.MiniJogoPalavras);

    }

}