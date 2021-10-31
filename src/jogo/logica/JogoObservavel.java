package jogo.logica;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import static jogo.Constantes.JOGO;


public class JogoObservavel {

    private PropertyChangeSupport pcs;
    private JogoMaqEstados jogoMaqEstados;


    public JogoObservavel(JogoMaqEstados jogoMaqEst) {
        this.jogoMaqEstados = jogoMaqEst;
        pcs = new PropertyChangeSupport(jogoMaqEstados);
    }

    // o argumento recebido por listenar vai passar a fazer parte da colecao de observadores
    // geridos pelo objeto propertyCangeSupport, desta classe;

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    // funcoes de atualizacao da informacao

    public void iniciarJogo() {
        jogoMaqEstados.iniciarJogo();
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void sortearJogador(int modoJogo, String jogadorA, String jogadorB) {
        jogoMaqEstados.sortearJogador(modoJogo, jogadorA, jogadorB);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void escolherColuna(int coluna) {
        jogoMaqEstados.escolherColuna(coluna);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void jogarPecaEspecial(int coluna, int jogador) {
        jogoMaqEstados.jogarPecaEspecial(coluna, jogador);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void ganhouMiniJogoCalculos(boolean ganhou) {
        jogoMaqEstados.ganhouMiniJogoCalculos(ganhou);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void ganhouMiniJogoPalavras(boolean ganhou) {
        jogoMaqEstados.ganhouMiniJogoPalavras(ganhou);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void terminarJogo() {
        jogoMaqEstados.terminarJogo();
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void escolherNomes(int modoJogo) {
        jogoMaqEstados.escolherNomes(modoJogo);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void voltarAtras(int jogador, int numvoltasAtras) {
        jogoMaqEstados.voltarAtras(jogador, numvoltasAtras);
        pcs.firePropertyChange(JOGO, false, true);

    }

    public void replayJogada() {
        jogoMaqEstados.replayJogada();
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void decisao(int opcao) {
        jogoMaqEstados.decisao(opcao);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void redo() {
        jogoMaqEstados.redo();
        pcs.firePropertyChange(JOGO, false, true);
    }



    // funcoes de consulta de informacao
    // delegam a consulta na classe da logica

    public char[][] getTabuleiro() {
        return jogoMaqEstados.getTabuleiro();
    }

    public Situacao getSituacaoAtual() {
        return jogoMaqEstados.getSituacao();
    }

    public int getModoJogo() {
        return jogoMaqEstados.getModoJogo();
    }

    public int getJogadas() {
        return jogoMaqEstados.getJogadas();
    }

    public int getJogadasA() {
        return jogoMaqEstados.getJogadasA();
    }

    public int getJogadasB() {
        return jogoMaqEstados.getJogadasB();
    }

    public int getJogadorJogar() {
        return jogoMaqEstados.getJogadorJogar();
    }

    public int getCreditosJogador(int num) {
        return jogoMaqEstados.getCreditosJogador(num);
    }

    public int getPecaEsp(int jog) {
        return jogoMaqEstados.getPecaEsp(jog);
    }

    public char getPeca(int jog) {
        return jogoMaqEstados.getPeca(jog);
    }

    public boolean verificaColunaVazia(int col) {
        return jogoMaqEstados.verificaColunaVazia(col);
    }

    public int gerarColuna() {
        return jogoMaqEstados.gerarColuna();
    }

    public boolean getEstadoColuna(int coluna) {
        return jogoMaqEstados.getEstadoColuna(coluna);
    }

    public void clearTabuleiro() {
        jogoMaqEstados.clearTabuleiro();
    }

    public int getVencedor() {
        return jogoMaqEstados.vencedor();
    }

    //FUNCOES PARA O GRAVA/CARREGA/REPLAY

    public void setReplayCarregado() {
        jogoMaqEstados.setReplayCarregado();
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void gravarJogoFX(File nomeFicheiro) {
        jogoMaqEstados.gravarJogoFX(nomeFicheiro);
    }

    public void carregarJogoFX(File nomeFicheiro) {
        jogoMaqEstados.carregarJogoGuardadoFX(nomeFicheiro);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public void gravarReplay(String nomeFicheiro) {
        jogoMaqEstados.gravarReplay(nomeFicheiro);
    }

    public void carregarReplayFX(File nomeFicheiro) {
        jogoMaqEstados.carregarReplayFX(nomeFicheiro);
        pcs.firePropertyChange(JOGO, false, true);
    }

    public int getTamanhoPasta(String replays) {
        return jogoMaqEstados.getTamanhoPasta(replays);

    }

    public String getNome(int jogador) {
        return jogoMaqEstados.getNome(jogador);
    }


//MiniJogo Palavras


    public boolean palavrasCertas(String palavras, String frase, long tempoDemorado, int tempoMaximo) {
        return jogoMaqEstados.palavrasCertas(palavras, frase, tempoDemorado, tempoMaximo);
    }

    public long contagem() {
        return jogoMaqEstados.contagem();
    }

    public long tempoDemorado(long inicioContagem) {
        return jogoMaqEstados.tempoDemorado(inicioContagem);
    }

    public int TempoMaximo(String palavras) {
        return jogoMaqEstados.TempoMaximo(palavras);
    }

    public String lerPalavrasFicheiro() {
        return jogoMaqEstados.lerPalavrasFicheiro();
    }

    public String mostraResultadoMiniJogoPalavras(String palavras, String frase, long tempoDemorado, int tempoMaximo) {
        return jogoMaqEstados.mostraResultadoMiniJogoPalavras(palavras, frase, tempoDemorado, tempoMaximo);
    }


//MiniJogo Calculos

    public int [] calculosDoMiniJogo(){
        return jogoMaqEstados.calculosDoMiniJogo();
    }

    public String mostraCalculos(int [] numeros){
        return jogoMaqEstados.mostraCalculos(numeros);
    }


}

