package jogo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jogo.iu.gui.Root;
import jogo.logica.JogoMaqEstados;
import jogo.logica.JogoObservavel;


public class App4EmLinha extends Application{

    @Override
    public void start(Stage janelaPrincipal) {


        JogoMaqEstados jogoMaqEstados = new JogoMaqEstados();
        JogoObservavel jogoObservavel = new JogoObservavel(jogoMaqEstados);

        //Janela Principal
        Root root = new Root(jogoObservavel);
        Scene scene = new Scene(root,1250,650);

        janelaPrincipal.setScene(scene);
        janelaPrincipal.setTitle("4 em Linha");
        janelaPrincipal.setMaxWidth(Double.MAX_VALUE); janelaPrincipal.setMaxWidth(Double.MAX_VALUE);
        janelaPrincipal.setMinWidth(1300); janelaPrincipal.setMinHeight(700);
         janelaPrincipal.setResizable(true);
        //janelaPrincipal.setFullScreen(true);
        janelaPrincipal.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
