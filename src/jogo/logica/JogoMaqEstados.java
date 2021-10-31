package jogo.logica;

import jogo.logica.dados.Jogador;
import jogo.logica.dados.Jogo;
import jogo.logica.estados.EsperaJogadorA;
import jogo.logica.estados.EsperaJogadorB;
import jogo.logica.estados.IEstado;
import jogo.logica.estados.Inicio;
import jogo.logica.memento.CareTaker;

import java.io.File;
import java.security.PublicKey;
import java.util.List;

public class JogoMaqEstados {

    private IEstado atual; // referencias para estado atual
    private Jogo jogo;  // referencia para os dados do jogo
    private CareTaker careTaker;


    public JogoMaqEstados() {
        jogo = new Jogo();
        atual = new Inicio(jogo);
        careTaker = new CareTaker(jogo);

    }

    // registar um  novo estado activo
    private void setEstado(IEstado atual) {
        this.atual = atual;
    }


    //----- Logs -----

    public List<String> getLog() {
        return jogo.getLog();
    }

    public List<String> getLogCompleto() {
        return jogo.getLogCompleto();
    }

    public void limparLog() {
        jogo.limparLog();
    }


    //Metodos para definir o estado atual

    public void iniciarJogo() {
        setEstado(atual.iniciarJogo()); // atual = atual.iniciarJogo();
    }

    public void sortearJogador(int modoJogo, String jogadorA, String jogadorB) {
        setEstado(atual.sortearJogador(modoJogo, jogadorA, jogadorB));
    }

    public void escolherColuna(int coluna) {
        careTaker.gravaMemento();
        setEstado(atual.escolherColuna(coluna));
    }


    public void ganhouMiniJogoCalculos(boolean ganhou) {
        setEstado(atual.ganhouMiniJogoCalculos(ganhou));
    }

    public void ganhouMiniJogoPalavras(boolean ganhou) {
        setEstado(atual.ganhouMiniJogoPalavras(ganhou));
    }

    public void decisao(int opcao) {
        setEstado(atual.decisao(opcao));
    }

    public void jogarPecaEspecial(int coluna, int jogador) {
        careTaker.gravaMemento();
        setEstado(atual.jogarPecaEspecial(coluna, jogador));
    }

    public void terminarJogo() {
        careTaker.clear();
        jogo.limparLog();
        jogo.limparLogCompleto();
        setEstado(atual.terminarJogo());
    }

    public void escolherNomes(int modoJogo) {
        setEstado(atual.escolherNomes(modoJogo));
    }

    public void voltarAtras(int jogador, int numvoltasAtras) {

        boolean impede = false;
        // caso tente voltar atras na jogada inicial
        if (jogo.getTotalJogadas() - 1 == 0)
            impede = true;

        if (!impede || getJogador(jogador).getCreditos() != 0) {
            if (jogador == 0)
                jogo.resetJogadasA(); // colocar as jogadas para a peca especial do jogador a 0
            else
                jogo.resetJogadasB();
        }

        if (!impede || getJogador(jogador).getCreditos() != 0) {
            for (int i = 0; i < numvoltasAtras; i++) {
                jogo.decCreditos(jogador);
                careTaker.undo();
            }
        }
        jogo.adicionarLog("O Jogador '" + jogo.getNome(0) + "' voltou a " + numvoltasAtras + " jogada(s) atrás!");

        setEstado(atual.voltarAtras());
    }

    public void replayJogada() {
        setEstado(atual.replayJogada());
    }

    public void redo() {
        careTaker.redo();
    }


    public Situacao getSituacao() {
        return atual.getSituacao();
    }


    //Metodos para aceder à informacao relativa ao dados do jogo

    public char[][] getTabuleiro() {
        return jogo.getTabuleiro();
    }

    public void clearTabuleiro() {
        jogo.clearTabuleiro();
    }

    public int gerarColuna() {
        return jogo.gerarColuna();
    }

    public boolean getEstadoColuna(int coluna) {
        return jogo.verificaColunaPreenchida(coluna);
    }

    public boolean verificaColunaVazia(int col) {
        return jogo.verificaColunaVazia(col);
    }

    public int getJogadas() {
        return jogo.getTotalJogadas();
    }

    public int getJogadasA() {
        return jogo.getJogadasA();
    }

    public int getJogadasB() {
        return jogo.getJogadasB();
    }

    public Jogador getJogador(int indice) {
        return jogo.getJogador(indice);
    }

    public int getCreditosJogador(int num) {
        return jogo.getCreditos(num);
    }

    public int getPecaEsp(int jog) {
        return jogo.getPecaEspecial(jog);
    }

    public char getPeca(int jog) {
        return jogo.getPeca(jog);
    }

    public String getNome(int jog) {
        return jogo.getNome(jog);
    }

    public int vencedor() {
        return jogo.getVencedor();
    }

    public int getTamanhoPasta(String nomePasta) {
        return jogo.getTamanhoPasta(nomePasta);
    }

    public int getModoJogo() {
        return jogo.getMogoJogo();
    }

    public int getJogadorJogar() {
        return jogo.getJogadorJogar();
    }


    //MiniJogo Palavras

    public boolean palavrasCertas(String palavras, String frase, long tempoDemorado, int tempoMaximo){
        return jogo.palavrasCertas(palavras,frase,tempoDemorado,tempoMaximo);
    }

    public boolean getPalavrasCertas() {
        return jogo.getPalavrasCertas();
    }

    public long contagem(){
        return jogo.contagem();
    }

    public long tempoDemorado(long inicioContagem){
        return jogo.tempoDemorado(inicioContagem);
    }

    public int TempoMaximo(String palavras){
        return jogo.TempoMaximo(palavras);
    }

    public String lerPalavrasFicheiro(){
        return jogo.lerPalavrasFicheiro();
    }

    public String mostraResultadoMiniJogoPalavras(String palavras, String frase, long tempoDemorado, int tempoMaximo){
      return jogo.mostraResultadoMiniJogoPalavras(palavras,frase,tempoDemorado,tempoMaximo);
    }


    //Mini Jogo Calculos

    public  int[] calculosDoMiniJogo(){
        return jogo.calculosDoMiniJogo();
    }

    public String mostraCalculos(int [] numeros) {
        return jogo.mostraCalculos(numeros);
    }


    //GRAVAR/CARREGAR/REPLAYS

    public void setReplayCarregado() {
        jogo.setReplayCarregado();
    }

    public void gravarJogoFX(File nomeFicheiro) {
        jogo.guardarJogoFX(nomeFicheiro, careTaker, jogo);
    }

    public void carregarJogoGuardadoFX(File nomeFich) {

        Object[] jogoCarregado;

        jogoCarregado = jogo.carregarSaveGameFX(nomeFich);
        this.careTaker = (CareTaker) jogoCarregado[0];
        this.jogo = (Jogo) jogoCarregado[1];

        //ver qual foi o ultimo jogador a jogar
        if (jogo.getJogadorJogar() == 0)
            setEstado(new EsperaJogadorA(jogo, 1));
        else
            setEstado(new EsperaJogadorB(jogo, 1));

    }

    public void carregarReplayFX(File nomeFich) {


        Object[] jogoCarregado;
        this.careTaker = null;

        jogoCarregado = jogo.carregarReplayFX(nomeFich);
        this.careTaker = (CareTaker) jogoCarregado[0];
        this.jogo = (Jogo) jogoCarregado[1];


        //ver qual foi o ultimo jogador a jogar
        this.jogo.setModoJogo(4); // modo Replay
        careTaker.reiniciar();

        if (jogo.getJogadorJogar() == 0) {
            setEstado(new EsperaJogadorA(jogo, 1));
        } else
            setEstado(new EsperaJogadorB(jogo, 1));

    }

    public void gravarReplay(String nomeFicheiro) {

        jogo.guardarReplay(nomeFicheiro, careTaker, jogo);
    }

}

/*

    public String mostraJogos() {
        return jogo.mostraSaveGames();
    }

    public String mostraReplays() {
        return jogo.mostraReplays();
    }

    public boolean verificaNomeSaveGame(String nomeFich) {
        return jogo.verificaNomeSaveGame(nomeFich);
    }

    public boolean verificaNomeReplay(String nomeFich) {
        return jogo.verificaNomeReplay(nomeFich);
    }


    public boolean verificaPasta(File nomePasta) {
        return jogo.verificaPasta(nomePasta);
    }

    public int getMiniJogoJogadorA() {
        return jogo.getMiniJogoJogadorA();
    }

    public int getMiniJogoJogadorB() {
        return jogo.getMiniJogoJogadorB();
    }

    public void gravarJogo(String nomeFicheiro) {
        jogo.guardarJogo(nomeFicheiro, careTaker, jogo);
    }

    public void carregarJogoGuardado(String nomeFich) {

        Object[] jogoCarregado;

        jogoCarregado = jogo.carregarSaveGame(nomeFich);
        this.careTaker = (CareTaker) jogoCarregado[0];
        this.jogo = (Jogo) jogoCarregado[1];

        //ver qual foi o ultimo jogador a jogar
        if (jogo.getJogadorJogar() == 0)
            setEstado(new EsperaJogadorA(jogo, 1));
        else
            setEstado(new EsperaJogadorB(jogo, 1));

    }

    public void carregarReplay(String nomeFich) {

        Object[] jogoCarregado;

        jogoCarregado = jogo.carregarReplay(nomeFich);
        this.careTaker = (CareTaker) jogoCarregado[0];
        this.jogo = (Jogo) jogoCarregado[1];


        //ver qual foi o ultimo jogador a jogar
        this.jogo.setModoJogo(4); // modo Replay
        careTaker.reiniciar();

        if (jogo.getJogadorJogar() == 0) {
            setEstado(new EsperaJogadorA(jogo, 1));
        } else
            setEstado(new EsperaJogadorB(jogo, 1));
    }

*/

