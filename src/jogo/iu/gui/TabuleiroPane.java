package jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.iu.gui.recursos.CSSManager;
import jogo.logica.JogoObservavel;

import static jogo.Constantes.JOGO;

public class TabuleiroPane extends GridPane {

    private JogoObservavel jogoObservavel;


    char[][] Tab;

    public TabuleiroPane(JogoObservavel jogoObservavel) {
       // CSSManager.setCSS(this,"estilos.css");
        this.jogoObservavel = jogoObservavel;
        criarVista();
        registarListeners();
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
        setId("tabuleiro");
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);
        setHgap(5);  setVgap(5);

    }


    private void atualiza() {


        Tab = jogoObservavel.getTabuleiro();

        this.getChildren().clear();

        if (Tab == null) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    Circle bola = new Circle(25);
                    bola.setFill(Color.WHITE);
                    add(bola, j, i);
                }
            }
        } else {
            /*
            System.out.println("\n           Tabuleiro do Jogo: ");
            for (int i = 0; i < 6; i++) {
                System.out.print("          ");
                for (int j = 0; j < 7; j++) {
                    System.out.printf("%c  ", Tab[i][j]);
                }
                System.out.println();
            }
            System.out.print("          ");
            for (int i = 1; i <= 7; i++) {
                System.out.printf("%d  ", i);
            }
            System.out.print("\n\n");
*/
            for (int i = 0; i < Tab.length; i++) {
                for (int j = 0; j < Tab[0].length; j++) {
                    Circle bola = new Circle(25);
                    if (Tab[i][j] == '_') {
                        bola.setFill(Color.WHITE);
                    } else if (Tab[i][j] == 'O') {
                        bola.setFill(Color.RED);
                    } else if (Tab[i][j] == 'X') {
                        bola.setFill(Color.YELLOW);
                    }
                    add(bola, j, i);
                }
            }
        }


    }


    void registarListeners(){






    }

}
