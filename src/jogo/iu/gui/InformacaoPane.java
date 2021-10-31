package jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;

import static jogo.Constantes.JOGO;

public class InformacaoPane extends BorderPane {

    private JogoObservavel jogoObservavel;
    Text jogador ;
    VBox infoJogador ;
    Text numJogadas;
    Text creditos;
    Text pecaEspecial;
    Circle bola;

    public InformacaoPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criarVista();
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
        jogador = new Text();
        infoJogador = new VBox();
        bola = new Circle(30);
        numJogadas = new Text();
        creditos = new Text();
        pecaEspecial = new Text();

        jogador.setTextAlignment(TextAlignment.CENTER);  jogador.setFill(Color.WHITE);   jogador.setStyle("-fx-font: 14 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        numJogadas.setTextAlignment(TextAlignment.CENTER);  numJogadas.setFill(Color.WHITE);   numJogadas.setStyle("-fx-font: 14 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        creditos.setTextAlignment(TextAlignment.CENTER);  creditos.setFill(Color.WHITE);   creditos.setStyle("-fx-font: 14 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");
        pecaEspecial.setTextAlignment(TextAlignment.CENTER);  pecaEspecial.setFill(Color.WHITE);   pecaEspecial.setStyle("-fx-font: 14 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");

        setPrefWidth(200);
        infoJogador.setAlignment(Pos.CENTER);
        infoJogador.setSpacing(10);


    }


    private void atualiza() {

        infoJogador.getChildren().clear();

        if (!(jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorA || jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorB)) {

            jogador.setText("Informações\nsobre os\nJogadores");
            jogador.setTextAlignment(TextAlignment.CENTER);  jogador.setFill(Color.WHITE);   jogador.setStyle("-fx-font: 24 'Helvetica Neue', sans-serif; -fx-font-weight: bold;");

            infoJogador.setPadding(new Insets(60,0,60,0));
            infoJogador.setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID,
                    null, new BorderWidths(3))));
            infoJogador.setBackground((new Background(new BackgroundFill(Color.rgb(23, 136, 212, 0.91),
                    CornerRadii.EMPTY,Insets.EMPTY))));

            infoJogador.getChildren().add(jogador);
            setCenter(infoJogador);
        }


        if (jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorA) {

            jogador.setText("Vez do Jogador \n'"+jogoObservavel.getNome(0)+"'\n");
            numJogadas.setText("\nJogadas A: " + jogoObservavel.getJogadasA() + " | Jogadas B: " + jogoObservavel.getJogadasB());
            creditos.setText("Créditos: " + jogoObservavel.getCreditosJogador(0));
            pecaEspecial.setText("Peça Especial: " + jogoObservavel.getPecaEsp(0));
           // System.out.println(" | Peça: '" + jogoObservavel.getPeca(0) + "'\n | Peca especial: " + jogoObservavel.getPecaEsp(0));

            infoJogador.setPadding(new Insets(60,0,60,0));
            infoJogador.setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID,
                    null, new BorderWidths(3))));
            infoJogador.setBackground((new Background(new BackgroundFill(Color.rgb(23, 136, 212, 0.91),
                    CornerRadii.EMPTY,Insets.EMPTY))));


            bola.setFill(Color.RED);


            infoJogador.getChildren().addAll(jogador,bola,numJogadas,creditos,pecaEspecial);
            setCenter(infoJogador);

        }
        else if (jogoObservavel.getSituacaoAtual() == Situacao.EsperaJogadorB) {

            jogador.setText("Vez do Jogador \n'"+jogoObservavel.getNome(1)+"'\n");
            numJogadas.setText("\nJogadas A: " + jogoObservavel.getJogadasA() + " | Jogadas B: " + jogoObservavel.getJogadasB());
            creditos.setText("Créditos: " + jogoObservavel.getCreditosJogador(1));
            pecaEspecial.setText("Peça Especial: " + jogoObservavel.getPecaEsp(1));
            //System.out.println(" | Peça: '"+jogoObservavel.getPeca(1)+"'\n | Peca especial: "+jogoObservavel.getPecaEsp(1));

            infoJogador.setPadding(new Insets(60,0,60,0));
            infoJogador.setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID,
                    null, new BorderWidths(3))));
            infoJogador.setBackground((new Background(new BackgroundFill(Color.rgb(23, 136, 212, 0.91),
                    CornerRadii.EMPTY,Insets.EMPTY))));


            bola.setFill(Color.YELLOW);

            infoJogador.getChildren().addAll(jogador,bola,numJogadas,creditos,pecaEspecial);


            setCenter(infoJogador);

        }



    }

}

