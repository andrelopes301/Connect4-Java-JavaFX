package jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jogo.iu.gui.estados.*;
import jogo.iu.gui.recursos.CSSManager;
import jogo.logica.JogoObservavel;

import javax.imageio.stream.FileCacheImageInputStream;

import static jogo.Constantes.JOGO;

public class PanePrincipal extends BorderPane {
    private JogoObservavel jogoObservavel;


    VBox dados;
    StackPane menu;
    private Label tabuleiroLabel;
    private Label infoLabel;
    private Label modoJogo;
    HBox botoes, botoesPecaEspecial_A, botoesPecaEspecial_B;


    InicioPane inicioPane;
    ModoJogoPane modoJogoPane;
    EscolherNomePane escolherNomePane;
    JogadorAPane jogadorAPane;
    JogadorBPane jogadorBPane;
    DecisaoMiniJogoPane decisaoMiniJogoPane;
    MiniJogoCalculosPane miniJogoCalculosPane;
    MiniJogoPalavrasPane miniJogoPalavrasPane;
    TabuleiroPane tabuleiroPane;
    FinalJogoPane finalJogoPane;


    public PanePrincipal(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        CSSManager.setCSS(this,"estilos.css");
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


        /*---------------------------------------------------------------------------------------*/
        //PANE TOTAL (JANELA TODA)

      //  setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID,
       //         null, new BorderWidths(5))));

        /*---------------------------------------------------------------------------------------*/
        //Hbox para o texto 4 em Linha  (HBOX - TOPO)

        Text t = new Text();
        t.setText("4 EM LINHA");
        t.setId("h1");
        HBox hBox = new HBox(t);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30));

        setTop(hBox);

        /*---------------------------------------------------------------------------------------*/
        //Vbox para o TABULEIRO (VBOX - ESQUERDA)

        BorderPane caixaTabuleiro = new BorderPane();
        tabuleiroPane = new TabuleiroPane(jogoObservavel);
        caixaTabuleiro.setPadding(new Insets(50,75,0,75));
        botoes = new BotoesColunasPane(jogoObservavel);
        botoes.setVisible(false);
        botoesPecaEspecial_A = new BotoesPecaEspecialPane(jogoObservavel,0);
        botoesPecaEspecial_B = new BotoesPecaEspecialPane(jogoObservavel,1);
        botoesPecaEspecial_A.setVisible(false);
        botoesPecaEspecial_B.setVisible(false);
        StackPane caixa = new StackPane();
        caixa.getChildren().addAll(botoes,botoesPecaEspecial_A,botoesPecaEspecial_B);
        caixaTabuleiro.setTop(tabuleiroPane);
        caixaTabuleiro.setCenter(caixa);
        setLeft(caixaTabuleiro);

        /*---------------------------------------------------------------------------------------*/
        //Vbox para o dados do jogo (VBOX - CENTRO)


        InformacaoPane informacaoPane = new InformacaoPane(jogoObservavel);

        dados = new VBox();
        dados.setAlignment(Pos.CENTER);
        dados.getChildren().add(informacaoPane);
        dados.setPadding(new Insets(30,0,50,0));
        setCenter(dados);

        /*---------------------------------------------------------------------------------------*/
        //StackPane para os estados (StackPane - DIREITA)

        inicioPane = new InicioPane(jogoObservavel);
        modoJogoPane = new ModoJogoPane(jogoObservavel);
        escolherNomePane = new EscolherNomePane(jogoObservavel);
        jogadorAPane = new JogadorAPane(jogoObservavel,botoes,botoesPecaEspecial_A);
        jogadorBPane = new JogadorBPane(jogoObservavel,botoes, botoesPecaEspecial_B);
        decisaoMiniJogoPane = new DecisaoMiniJogoPane(jogoObservavel);
        miniJogoCalculosPane = new MiniJogoCalculosPane(jogoObservavel);
        miniJogoPalavrasPane = new MiniJogoPalavrasPane(jogoObservavel);
        finalJogoPane = new FinalJogoPane(jogoObservavel);



        menu = new StackPane(inicioPane, modoJogoPane,escolherNomePane,jogadorAPane,jogadorBPane,decisaoMiniJogoPane,miniJogoCalculosPane,miniJogoPalavrasPane, finalJogoPane);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(0,60,60,60));

        setRight(menu);
    }

    private void atualiza() {

    }


    void registarListeners() {


    }

}





