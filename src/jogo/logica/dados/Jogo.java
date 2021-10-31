package jogo.logica.dados;

import jogo.logica.memento.CareTaker;
import jogo.logica.memento.IMementoOriginator;
import jogo.logica.memento.Memento;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Jogo implements Serializable, IMementoOriginator {

    // Dados do jogo
    private int total_jogadas, jogadasA, jogadasB;
    private int jogador;
    private final int lin = 6;
    private final int col = 7;
    private int vencedor;
    private int mogoJogo;
    private char[][] tabuleiro;
    private ArrayList<Jogador> jogadores;
    static boolean jogoCarregado;

    //----- Minijogos -----
    int calculosCertos;
    boolean palavrasCertas;
    int miniJogoJogA;
    int miniJogoJogB;

    //----- Logs -----

    ArrayList<String> log = new ArrayList<>();
    ArrayList<String> logCompleto = new ArrayList<>();

    public void adicionarLog(String str){
        log.add(str);
        logCompleto.add(str);
    }
    public void limparLog(){
        log.clear();
    }
    public void limparLogCompleto(){
        logCompleto.clear();
    }
    public List<String> getLog(){
        return log;
    }
    public List<String> getLogCompleto(){
        return logCompleto;
    }


    public Jogo(){}

    public void iniciar() {

        total_jogadas = 0;
        jogadasA = 0; jogadasB = 0;
        jogador = 0;
        vencedor = 0;
        mogoJogo = 0;
        tabuleiro = new char[lin][col];
        jogadores = new ArrayList<>();
        jogoCarregado = false;

        //Minijogos
        calculosCertos = 0; palavrasCertas = false;
        miniJogoJogA = 0; miniJogoJogB = 0;


        //Inicializacao do Tabuleiro
        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                tabuleiro[i][j] = '_';
            }
        }
        // numero de colunas -> tabuleiro[0].length
        // numero de linhas  -> tabuleiro.length);
    }

    public int sortearJogador() {

        Random num = new Random();
        int jogador = num.nextInt(2)+1;

        // 1 = Jogador A
        // 2 = Jogador B

        return jogador;
    }

    public int verificaFimJogo(char letra){

        if (verificaLinhas(letra) == 1 || verificaColunas(letra) == 1
                || verificaDiagonais(letra) == 1){
            return 1;
        }

        if(verificaTabuleiroPreenchido() == 1)
            return 2;

        return 0;
    }

    public void escolherColuna(int coluna, char peca_jogador){


        // colocar a peca do jogador
        for (int i = lin - 1; i >= 0; i--) {
            if (tabuleiro[i][coluna-1] == '_') {
                tabuleiro[i][coluna-1] = peca_jogador;
                break;
            }
        }

    }

    public int gerarColuna(){

        int coluna;

        Random randint = new Random();
        coluna = randint.nextInt(7) + 1;

        return  coluna;
    }

    public boolean verificaColunaVazia(int coluna) {

        int i,cont=0;

        for (i = 0; i < tabuleiro.length; i++)
            if (tabuleiro[i][coluna - 1] == '_')
                cont++;

        if (cont == tabuleiro.length)
            return true; // se a coluna estiver vazia

        return false;
    }

    public boolean verificaColunaPreenchida(int coluna){

        int linha, cont = 0;

        if (!(coluna < 1 || coluna > 7)) {
            for (linha = 0; linha < lin; linha++) {
                if (tabuleiro[linha][coluna - 1] != '_')
                    cont++;
                else
                    cont = 0;
            }
            return cont == linha; // caso a coluna esteja preenchida
        }
          return false;
    }

    public int verificaTabuleiroPreenchido(){

        int cont=0;

        for (int i = 0; i < col; i++) {
            if(verificaColunaPreenchida(i+1))
                cont++;
        }
        if(cont == col)
            return 1; // caso em que ocorreu um empate

        return 0;
    }

    public int verificaColunas(char letra){


        for (int i = 0; i < col; i++) { // ver para todas as colunas -> 7 colunas
            int cont = 0;
            for (int j = 0; j < lin; j++) { // ver linha a linha -> 6 linhas
                if (tabuleiro[j][i] == letra) {
                    cont++;
                    //System.out.println("Contador:" +cont);
                } else {
                    cont = 0;
                }
                if (cont == 4) {
                    return 1;
                }
            }
        }
        return 0;

    }

    public int verificaLinhas(char letra){

        for (int i = 0; i < lin; i++) {  // ver para todas as linhas -> 6 linhas
            int cont = 0;
            for (int j = 0; j < col; j++) { // ver coluna a coluna -> 7 colunas
                if (tabuleiro[i][j] == letra) {
                    cont++;
                //    System.out.println("Contador:" +cont);
                } else {
                    cont = 0;
                }
                if (cont == 4) {
                    return 1;
                }
            }
        }
        return 0;

    }

    public int verificaDiagonais(char letra){

        // Diagonais ascendentes
        for (int i=3; i < lin; i++){
            for (int j=0; j < col-3; j++){
                if (tabuleiro[i][j] == letra && tabuleiro[i-1][j+1] == letra
                        && tabuleiro[i-2][j+2] == letra && tabuleiro[i-3][j+3] == letra)
                    return 1;
            }
        }
        // Diagonais descendentes
        for (int i=3; i < lin; i++){
            for (int j=3; j < col; j++){
                if (tabuleiro[i][j] == letra && tabuleiro[i-1][j-1] == letra
                        && tabuleiro[i-2][j-2] == letra && tabuleiro[i-3][j-3] == letra)
                    return 1;
            }
        }

        return 0;
    }

    public boolean jogarPecaEspecial(int coluna, int jogador) {

        int i;

        if(!verificaColunaVazia(coluna)){
            for (i = 0; i < tabuleiro.length; i++)
                tabuleiro[i][coluna - 1] = '_';
        }

        usarPecaEspecial(jogador); // tirar a peça especial ao jogador

        return true; //fazer a coluna desaparecer com as peças jogadas
    }

    public void adicionaJogador(String nome, char peca){
        jogadores.add(new Jogador(nome,peca));
    }

    //GETS E SETS

    public int getTotalJogadas() {
        return total_jogadas;
    }

    public void incTotalJogadas() {
        total_jogadas += 1;
    }

    public void decJogadasA() {
            jogadasA -= 1;
    }

    public void decJogadasB() {
             jogadasB -= 1;
    }

    public int getJogadasA() {
        return jogadasA;
    }

    public int getJogadasB() {
        return jogadasB;
    }

    public void incJogadasA() {
        if(jogadasA > 4)
            jogadasA = 2;
        else
            jogadasA += 1;

    }

    public void incJogadasB() {

        if(jogadasB > 4)
            jogadasB = 2;
        else
            jogadasB += 1;
    }

    public void resetJogadasA(){
        jogadasA = 0;
    }

    public void resetJogadasB(){
        jogadasB = 0;
    }



    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public void clearTabuleiro(){
        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                tabuleiro[i][j] = '_';
            }
        }
    }



    public Jogador getJogador(int num){
        return jogadores.get(num);
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public int getCreditos(int num){
        return jogadores.get(num).getCreditos();
    }

    public int getPecaEspecial(int num){
        return jogadores.get(num).getPecaEspecial();
    }

    public char getPeca(int num){
        return jogadores.get(num).getPeca();
    }

    public String getNome(int num){
        return jogadores.get(num).getNome();
    }

    public void ganharPecaEspecial(int num) {
        jogadores.get(num).ganhaPecaEspecial();
    }

    public void usarPecaEspecial(int num) {
        jogadores.get(num).usaPecaEspecial();
    }

    public void decCreditos(int num){
        jogadores.get(num).decCreditos();
    }

    public void setVencedor(int jogador){

        if(jogador == 1) {
            vencedor = 1;
            return; // jogador A
        } else  if(jogador == 2) {
            vencedor = 2;
            return; // jogador B
        }

        vencedor = 0;
    }

    public void setModoJogo(int modo){
        this.mogoJogo = modo;
    }

    public int getMogoJogo() {
        return mogoJogo;
    }

    public int getJogadorJogar() {
        return jogador;
    }

    public void setJogador(int jog) {
        jogador = jog;
    }

    public int getVencedor() {
        return vencedor;
    }

    public int getMiniJogoJogadorA() {
        return miniJogoJogA;
    }

    public void setMiniJogoJogadorA(int miniJogoA) {
        miniJogoJogA = miniJogoA;
    }

    public int getMiniJogoJogadorB() {
        return miniJogoJogB;
    }

    public void setMiniJogoJogadorB(int miniJogoB) {
        miniJogoJogB = miniJogoB;
    }


    //MINI JOGO CALCULOS

    public  int[] calculosDoMiniJogo(){

        Random num = new Random();
        int [] nums = new int[4];

        nums[0] = num.nextInt(20)+1; // 1ºNumero
        nums[1] = num.nextInt(4)+1; // Operador
        nums[2] = num.nextInt(20)+1; // 2ºNumero
        nums[3] = 0; // resultado final

        switch (nums[1]) {
            case 1:
                nums[3] = nums[0] + nums[2]; // soma
                break;
            case 2:
                nums[3] = nums[0] - nums[2]; // subtracao
                break;
            case 3:
                nums[3] = nums[0] * nums[2]; // multiplicacao
                break;
            case 4:
                nums[3] = nums[0] / nums[2]; // divisao
                break;
        }

        return nums;

    }

    public boolean miniJogoCalculos(){
        int certas = 0;
        int [] numeros;
        int resposta;
        long inicioContagem = System.currentTimeMillis();
        long fimContagem = inicioContagem + 30*1000; // 30 seg * 1000 ms/seg

        while (System.currentTimeMillis() < fimContagem)   {
            numeros = calculosDoMiniJogo();
            System.out.println(mostraCalculos(numeros));
            resposta = respostaCalculo();

            if(resposta == numeros[3])
                certas += 1;
            if (certas >= 5){
                setCalculosCertos(certas);
               // System.out.println("\n[INFO] Acertou "+certas+" calculos!");
                return true;
            }
        }

        return false;
    }

    public void setCalculosCertos(int certas) {
        calculosCertos = certas;
    }

    public int getCalculosCertos(){
        return calculosCertos;
    }

    public int respostaCalculo(){

        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt())
            sc.next();
        int resposta = sc.nextInt();

        return resposta;
    }

    public String mostraCalculos(int[] numeros) {

        StringBuilder sb = new StringBuilder();

        if(numeros[1] == 1)
            sb.append(numeros[0]).append(" ").append("+").append(" ").append(numeros[2]).append(" = ");
        else if(numeros[1] == 2)
            sb.append(numeros[0]).append(" ").append("-").append(" ").append(numeros[2]).append(" = ");
        else if(numeros[1] == 3)
            sb.append(numeros[0]).append(" ").append("*").append(" ").append(numeros[2]).append(" = ");
        else
            sb.append(numeros[0]).append(" ").append("/").append(" ").append(numeros[2]).append(" = ");

        return sb.toString();
    }


    //MINI JOGO PALAVRAS

    public boolean miniJogoPalavras(){

        String palavras = lerPalavrasFicheiro();
        String frase;
        TempoMaximo(palavras);
        int tempoMaximo = TempoMaximo(palavras);
        System.out.println(palavras);
        long inicioContagem = contagem();
        frase = respostaPalavras();

        long tempoDemorado = tempoDemorado(inicioContagem);
        System.out.println(mostraResultadoMiniJogoPalavras(palavras,frase,tempoDemorado,tempoMaximo));
        palavrasCertas(palavras,frase,tempoDemorado,tempoMaximo);

        if(palavras.equals(frase) && tempoDemorado <= tempoMaximo)
            return true;

        return false;
    }

    public String lerPalavrasFicheiro() {

        try {
            Scanner sc = new Scanner(new File("palavras.txt"));
            ArrayList<String> palavrasLidas = new ArrayList<>();

            while (sc.hasNext()) {
                palavrasLidas.add(sc.next()); //adicionar palavras ao array
            }
            sc.close(); // fechar o ficheiro

            StringBuilder palavras = new StringBuilder();
            int random = new Random().nextInt(palavrasLidas.size());
            palavras.append(palavrasLidas.get(random)); //adicionar 1ª palavra à string

            // para evitar um espaço em branco no fim da ultima palavra da string
            for (int i = 1; i < 5; i++) {
                random = new Random().nextInt(palavrasLidas.size());
                palavras.append(" ").append(palavrasLidas.get(random));
            }

            return palavras.toString(); // return das 5 palavras

        } catch (FileNotFoundException e) {
            System.err.println("Ficheiro nao encontrado!");
            //e.printStackTrace();
        }
        return "Ocorreu um problema!";

    }

    public String mostraResultadoMiniJogoPalavras(String palavras, String frase, long tempoDemorado, int tempoMaximo){

        StringBuilder sb = new StringBuilder();

        System.out.println("\n[INFO] Tempo demorado: " + tempoDemorado +"/"+tempoMaximo+" segundos");
        System.out.println("\n[INFO] Palavras: " + palavras +"\nfrase"+frase);


        sb.append("\nTempo demorado: " + tempoDemorado +"/"+tempoMaximo+" segundos\n\n");
        if(palavras.equals(frase))
             sb.append("As palavras coincidem!");
        else
            sb.append("As palavras não coincidem!");

        return sb.toString();
    }

    public boolean palavrasCertas(String palavras, String frase, long tempoDemorado, int tempoMaximo){
        if(palavras.equals(frase) && tempoDemorado <= tempoMaximo)
            return true;
        else
           return false;
    }

    public boolean getPalavrasCertas() {
        return palavrasCertas;
    }

    public long contagem(){
        return System.currentTimeMillis();
    }

    public long tempoDemorado(long inicioContagem){
        long fimContagem = System.currentTimeMillis();
        long tempoDemorado = (fimContagem - inicioContagem) / 1000;
    return tempoDemorado;
    }

    public int TempoMaximo(String palavras){
        return palavras.length()/2;
    }

    public String respostaPalavras(){
        Scanner sc = new Scanner(System.in);
        String frase;
        do {
            frase = sc.nextLine().trim();
        } while (frase.isEmpty());

        return frase;
    }

    public void setReplayCarregado(){
        jogoCarregado = true;
    }


    //VOLTAR ATRAS (MEMENTO)

    @Override
    //guardar
    public Memento getMemento() throws IOException {

        Memento m = new Memento(this);
        return m;
    }

    @Override
    //restaurar
    public void setMemento(Memento m) throws IOException, ClassNotFoundException {

        Jogo jogo = (Jogo) m.getSnapshot();
        // definir apenas o tabuleiro e o jogador de jogadas anteriores
        tabuleiro = jogo.tabuleiro;
        jogador = jogo.getJogadorJogar();

        //Para definir tbm o numero de jogadas, e dados de jogadores anteriores (Situacao de Replay)
        if (jogoCarregado){
            total_jogadas = jogo.getTotalJogadas();
            jogadores = jogo.jogadores;
            jogadasA = jogo.jogadasA;
            jogadasB = jogo.jogadasB;
        }

    }


    // GRAVACAO/CARREGAMENTO DO JOGO

    public void guardarJogo(String nomeFich,CareTaker ct, Jogo jg) {

        ObjectOutputStream oos = null;
        //GUARDAR JOGO NO FICHEIRO
        try {
            FileOutputStream fos = new FileOutputStream("savegames\\"+nomeFich+".bin");
            oos = new ObjectOutputStream(fos);
            Object[] savegame = {ct,jg};
            oos.writeObject(savegame);
            oos.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Object[] carregarSaveGame(String nomeFich)  {

        ObjectInputStream ois = null;
        Object[] savegame = null;
        //LER JOGO DO FICHEIRO

        try {
            FileInputStream fis = new FileInputStream("savegames\\"+nomeFich);
            ois = new ObjectInputStream(fis);
            savegame = (Object[]) ois.readObject();
            ois.close();
            return savegame;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void guardarJogoFX(File nomeFich,CareTaker ct, Jogo jg) {

        ObjectOutputStream oos = null;
        //GUARDAR JOGO NO FICHEIRO
        try {
            FileOutputStream fos = new FileOutputStream(nomeFich);
            oos = new ObjectOutputStream(fos);
            Object[] savegame = {ct,jg};
            oos.writeObject(savegame);
            oos.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Object[] carregarSaveGameFX(File nomeFich)  {

        ObjectInputStream ois = null;
        Object[] savegame = null;
        //LER JOGO DO FICHEIRO

        try {
            FileInputStream fis = new FileInputStream(nomeFich);
            ois = new ObjectInputStream(fis);
            savegame = (Object[]) ois.readObject();
            ois.close();
            return savegame;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String mostraSaveGames(){

        File dir = new File("savegames");
        File[] listaFicheiros = dir.listFiles();
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss");

        for (int i = 0; i < listaFicheiros.length; i++) {
            if (listaFicheiros[i].isFile())
                sb.append("  | ").append(listaFicheiros[i].getName()).append("  (Data do jogo: ").append(sdf.format(new Date((listaFicheiros[i].lastModified()))) + ")\n");

        }
        return sb.toString();

    }

    public boolean verificaNomeSaveGame(String nomeFich) {

        File dir = new File("savegames");
        File[] listaFicheiros = dir.listFiles();

        for (int i = 0; i < listaFicheiros.length; i++) {
            if (listaFicheiros[i].getName().equals(nomeFich))
                return true;
        }
        return false;
    }

    public int getTamanhoPasta(String nomePasta){

        File dir = new File(nomePasta);
        File[] listaFicheiros = dir.listFiles();


        return listaFicheiros.length;
    }

    //REPLAYS

    public void guardarReplay(String nomeFich,CareTaker ct, Jogo jogo) {

        ObjectOutputStream oos = null;
        //GUARDAR JOGO NO FICHEIRO
        try {
            FileOutputStream fos = new FileOutputStream("replays\\"+nomeFich+".bin");
            oos = new ObjectOutputStream(fos);
            Object[] obj = {ct,jogo};
            oos.writeObject(obj);
            oos.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Object[] carregarReplay(String nomeFich)  {

        ObjectInputStream ois = null;
        //LER JOGO DO FICHEIRO
        try {
            FileInputStream fis = new FileInputStream("replays\\"+nomeFich);
            ois = new ObjectInputStream(fis);
            Object[] obj = (Object[]) ois.readObject();
            ois.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void guardarReplayFX(File nomeFich,CareTaker ct, Jogo jogo) {

        ObjectOutputStream oos = null;
        //GUARDAR JOGO NO FICHEIRO
        try {
            FileOutputStream fos = new FileOutputStream(nomeFich);
            oos = new ObjectOutputStream(fos);
            Object[] obj = {ct,jogo};
            oos.writeObject(obj);
            oos.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Object[] carregarReplayFX(File nomeFich)  {

        ObjectInputStream ois = null;
        //LER JOGO DO FICHEIRO
        try {
            FileInputStream fis = new FileInputStream(nomeFich);
            ois = new ObjectInputStream(fis);
            Object[] obj = (Object[]) ois.readObject();
            ois.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String mostraReplays(){

        File dir = new File("replays");
        File[] listaFicheiros = dir.listFiles();
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss");

        if(listaFicheiros.length <= 5)
        for (int i = 0; i < listaFicheiros.length; i++) {
            if (listaFicheiros[i].isFile())
                sb.append("  | ").append(listaFicheiros[i].getName()).append("  (Data do jogo: ").append(sdf.format(new Date((listaFicheiros[i].lastModified()))) + ")\n");
        }
        else
        {
            for (int i = listaFicheiros.length-5; i < listaFicheiros.length; i++) {
                if (listaFicheiros[i].isFile())
                    sb.append("  | ").append(listaFicheiros[i].getName()).append("  (Data do jogo: ").append(sdf.format(new Date((listaFicheiros[i].lastModified()))) + ")\n");
            }

        }

        return sb.toString();
    }

    public boolean verificaNomeReplay(String nomeFich) {

        File dir = new File("replays");
        File[] listaFicheiros = dir.listFiles();

        for (int i = 0; i < listaFicheiros.length; i++) {
            if (listaFicheiros[i].getName().equals(nomeFich))
                return true;
        }
        return false;
    }

    public boolean  verificaPasta(File dir) {
        String[] files = dir.list();
        return files.length == 0;
    }


}
