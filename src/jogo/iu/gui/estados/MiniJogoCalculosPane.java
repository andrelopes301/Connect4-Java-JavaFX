package jogo.iu.gui.estados;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class MiniJogoCalculosPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    Button btnComecar, btnContinuar;
    VBox caixaCentral;
    Text text, descricao;
    TextField respostaField;
    Label calculo, infoGanhou;
    HBox caixatexto;
    long inicioContagem, fimContagem;
    int [] numeros;
    int resposta;
    int certas = 0;
    boolean comecar = false;

    public MiniJogoCalculosPane(JogoObservavel jogoObservavel) {

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

        text = new Text("Mini-Jogo dos Cálculos");
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

        descricao = new Text("Tem 30 segundos para acertar em pelo menos 5 cálculos que lhe irão aparecer!");

        descricao.setTextAlignment(TextAlignment.CENTER);
        descricao.setStyle("-fx-font: 20 'Helvetica Neue', sans-serif; -fx-font-weight: normal;");
        descricao.setWrappingWidth(200);

        infoGanhou = new Label();
        infoGanhou.setStyle("-fx-text-fill: Black;-fx-font: 16 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        infoGanhou.setMaxWidth(300);
        infoGanhou.setAlignment(Pos.CENTER);

        calculo = new Label();
        calculo.setStyle("-fx-text-fill: Black;-fx-font: 20 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        calculo.setPadding(new Insets(0,30,0,30));
        calculo.setAlignment(Pos.CENTER);

        respostaField = new TextField("");
        respostaField.setPadding(new Insets(10));
        respostaField.setMaxWidth(50);
        setAlignment(respostaField,Pos.CENTER);

        caixaCentral.setAlignment(Pos.CENTER);
        caixaCentral.setSpacing(30);
        caixaCentral.getChildren().addAll(descricao,btnComecar);

        setTop(caixatexto);
        setCenter(caixaCentral);


    }


    private void registarListeners() {

            btnContinuar.setOnAction((ActionEvent e) -> {
                comecar = false;
                caixaCentral.getChildren().clear();
                caixaCentral.getChildren().addAll(descricao,btnComecar);
                respostaField = new TextField("");
                respostaField.setPadding(new Insets(10));
                respostaField.setMaxWidth(50);
                setAlignment(respostaField,Pos.CENTER);


                if(certas >= 5)
                    jogoObservavel.ganhouMiniJogoCalculos(true);
                else
                    jogoObservavel.ganhouMiniJogoCalculos(false);
        });


    }

    private void atualiza() {

        this.setVisible(jogoObservavel.getSituacaoAtual() == Situacao.MiniJogoCalculos);

        btnComecar.setOnAction((ActionEvent e) -> {
            certas = 0;
            inicioContagem = jogoObservavel.contagem();
            fimContagem = inicioContagem + 30*1000;
            caixaCentral.getChildren().clear();
            comecar = true;
            atualiza();
        });


        if(comecar) {
            if(jogoObservavel.contagem() >= fimContagem || (certas >= 5)) {
                caixaCentral.getChildren().clear();

                if(certas >= 5) // saber se ganhou o minijogo
                    infoGanhou.setText("Ganhou o MiniJogo e continua\na ser a sua vez de jogar!");
                else
                    infoGanhou.setText("Perdeu o MiniJogo e perdeu\na sua vez de jogar!");


                caixaCentral.getChildren().addAll(infoGanhou, btnContinuar);
                return;
            }

            numeros = jogoObservavel.calculosDoMiniJogo();
            System.out.println(jogoObservavel.mostraCalculos(numeros));
            calculo.setText(jogoObservavel.mostraCalculos(numeros));
            caixaCentral.getChildren().clear();
            caixaCentral.getChildren().addAll(calculo, respostaField);

            respostaField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        resposta = Integer.parseInt(respostaField.getText());
                        respostaField.setText("");

                        if (resposta == numeros[3]){
                            certas += 1;
                              System.out.println("acertou");
                        }else
                            System.out.println("errou");
                        atualiza();

                    }
                }
            });
        }
     }
}