/*
package jogo.iu.texto;

import jogo.logica.JogoMaqEstados;

import java.io.File;
import java.util.Scanner;

public class IUTexto {

    static Scanner sc = new Scanner(System.in);
    private JogoMaqEstados maqEstados;
    private IUTabuleiro iuTabuleiro;
    private boolean sair = false;
    private final int jogA = 0;
    private final int jogB = 1;
    private boolean mostraLogs = false;

    public IUTexto(JogoMaqEstados maqEstados, IUTabuleiro iuTabuleiro) {
        this.maqEstados = maqEstados;
        this.iuTabuleiro = iuTabuleiro;
    }

    public void mostrarLog(){
        for (String log:maqEstados.getLog()) {
            System.out.println("Log: ["+log+"]");
        }
        maqEstados.limparLog();
    }

    public void mostrarLogCompleto(){
        int cont = 1;
        for (String logs:maqEstados.getLogCompleto()) {
            System.out.println(cont+".  ["+logs+"]");
            cont++;
        }
    }

    public void iniciarJogo() {
        sair = false;
        while (!sair) {
            if(mostraLogs)
                mostrarLog();
            switch (maqEstados.getSituacao()) {
                case Inicio:
                    inicioUI();
                    break;
                case EsperaModoJogo:
                    modoJogoUI();
                    break;
                case EsperaNome:
                    esperaNomeUI();
                    break;
                case EsperaJogadorA:
                    esperarJogadorAIU();
                    break;
                case EsperaJogadorB:
                    esperarJogadorBIU();
                    break;
                case EsperaMiniJogo:
                    esperarMiniJogoIU();
                    break;
                case FimJogo:
                    fimJogoUI();
                    break;
            } }
    }

    private void inicioUI() {
        System.out.println("\n-------------- 4 EM LINHA --------------\n");
        iuTabuleiro.tabuleiroVazio();
        int op = escolheOpcao("Iniciar Jogo", "Carregar Jogo","Ver Replay dos ??ltimos 5 Jogos","Mostrar logs durante o jogo", "Sair");
        switch (op) {
            case 1:
                maqEstados.iniciarJogo();
                break;
            case 2:
                boolean nomeCorreto = false;
                String nomeFich;
                if(maqEstados.verificaPasta(new File("savegames"))) {
                    System.out.println("\n[INFO] N??o apresenta jogos guardados para carregar!");
                    return;
                }
                System.out.println("\n---------- Jogos Guardados ----------\n");
                System.out.println(maqEstados.mostraJogos());
                do {
                    nomeFich = pedeString("Nome do ficheiro: ");
                    nomeCorreto = maqEstados.verificaNomeSaveGame(nomeFich);
                    if(!nomeCorreto)
                        System.out.println("[INFO] O ficheiro que escreveu n??o existe. Insira novamente!\n");
                }while (!nomeCorreto);
                maqEstados.carregarJogoGuardado(nomeFich);
                break;
            case 3:
                menuReplayUI();
                break;
            case 4:
                if(mostraLogs)
                    mostraLogs = false;
                else
                    mostraLogs = true;
                System.out.println("\n[INFO] Mostrar Logs: " +mostraLogs);
                break;
            default:
                sair = true;
                break;
        }
    }

    private void menuReplayUI() {

        boolean nomeCorreto = false;
        String nomeFich;

        if(maqEstados.verificaPasta(new File("replays"))) {
            System.out.println("\n[INFO] N??o apresenta replays guardados!");
            return;
        }

        System.out.println("\n---------- Ultimos 5 Replays ----------\n");
        System.out.println(maqEstados.mostraReplays());

        do {
            nomeFich = pedeString("Nome do replay: ");
            nomeCorreto = maqEstados.verificaNomeReplay(nomeFich);
            if(!nomeCorreto)
                System.out.println("[INFO] O ficheiro que escreveu n??o existe. Insira novamente!\n");
        }while (!nomeCorreto);

        maqEstados.setReplayCarregado();
        maqEstados.carregarReplay(nomeFich);
    }

    private void modoJogoUI() {
        int modoJogo;
        System.out.println("Indique o modo de jogo que pretende jogar:\n");
        modoJogo = escolheOpcao("Jogador A(Humano)  VS  Jogador B(Humano)","Jogador A(Humano)  VS  Jogador B(Virtual)",
                "Jogador A(Virtual) VS  Jogador B(Virtual)\n","Voltar ao Menu Principal");

        if(modoJogo != 0)
            maqEstados.escolherNomes(modoJogo);
        else
            maqEstados.terminarJogo();
    }

    private void esperaNomeUI() {
        String jogadorA = "", jogadorB = "";
         if (maqEstados.getModoJogo() == 1) {
            jogadorA = pedeString("Introduza o nome do Jogador A: ");
            do{
                jogadorB = pedeString("Introduza o nome do Jogador B: ");
                if(jogadorA.equals(jogadorB))
                    System.out.println("[INFO] O nome do jogador B deve ser diferente do jogador A!");
            }while (jogadorA.equals(jogadorB));
        } else if(maqEstados.getModoJogo() == 2){
            jogadorA = pedeString("Introduza o nome do Jogador A: ");
            jogadorB = "Virtual";
        } else if(maqEstados.getModoJogo() == 3){
            jogadorA = "VirtualA";
            jogadorB = "VirtualB";
        }
        maqEstados.sortearJogador(maqEstados.getModoJogo(),jogadorA,jogadorB);
    }

    private void esperarMiniJogoIU() {

        if(maqEstados.getJogadorJogar() == 0){
             if (maqEstados.getMiniJogoJogadorA() == 0)
                 miniJogoCalculosInfo();
              else
                 miniJogoPalavrasInfo();
        } else{
            if (maqEstados.getMiniJogoJogadorB() == 0)
                miniJogoCalculosInfo();
             else
                miniJogoPalavrasInfo();
            }

            //esperar input para continuar
            sc.nextLine();

        //maqEstados.jogarMinijogo();

        if (maqEstados.getCalculosCertos() >= 5 || maqEstados.getPalavrasCertas())
            System.out.println("[INFO] Ganhou a pe??a especial e continua a ser a sua vez de jogar!\n");
        else
            System.out.println("[INFO] N??o ganhou a peca especial e perdeu a sua vez de jogar!\n");
    }

    private void esperarJogadorAIU() {

        informacaoJogador(jogA);
        if (maqEstados.getModoJogo() == 1 || maqEstados.getModoJogo() == 2) { // Hum vs Hum || Hum vs Comp
            System.out.println(" | Pe??a Especial: "+maqEstados.getPecaEsp(jogA));
            iuTabuleiro.mostraTabuleiro();
            //Apos 4 jogadas do jogador A
            if (maqEstados.getJogadasA() == 5 && maqEstados.getVerInfoMiniJogosA() == 0) {
                System.out.println("\nDeseja realizar um mini-jogo para tentar ganhar uma pe??a especial?" +"\n[Nota] Caso perca o mini-jogo, perde a sua vez de jogar!\n");
                int opcao = escolheOpcao("Sim", "N??o");
                if(opcao == 1 ) {
                    maqEstados.verificaJogadaMiniJogo(); return; }
                System.out.println();
            }
            menuJogadorHumano(jogA);
        }else if (maqEstados.getModoJogo() == 3)  // Comp vs Comp
            menuJogadorAutomatico(jogA);
        else if (maqEstados.getModoJogo() == 4) // Replay
            menuJogadorReplays();
    }

    private void esperarJogadorBIU() {

       informacaoJogador(jogB);
       if (maqEstados.getModoJogo() == 1) { // Hum vs Hum
            System.out.println(" | Pe??a Especial: "+maqEstados.getPecaEsp(jogB));
            iuTabuleiro.mostraTabuleiro();
            //Apos 4 jogadas do jogador B
           if (maqEstados.getJogadasB() == 5 && maqEstados.getVerInfoMiniJogosB() == 0) {
               System.out.println("\nDeseja realizar um mini-jogo para tentar ganhar uma pe??a especial?" +"\n[Nota] Caso perca o mini-jogo, perde a sua vez de jogar!\n");
               int opcao = escolheOpcao("Sim", "N??o");
               if(opcao == 1 ) {
                   maqEstados.verificaJogadaMiniJogo(); return;}
               System.out.println();
           }
           menuJogadorHumano(jogB);
        }else if (maqEstados.getModoJogo() == 2 || maqEstados.getModoJogo() == 3) { // ... vs Computador
           menuJogadorAutomatico(jogB);
        }else if (maqEstados.getModoJogo() == 4)  // Replay
           menuJogadorReplays();
    }

    private void fimJogoUI() {
        System.out.println("\n------------- FINAL DO JOGO -------------");
        iuTabuleiro.mostraTabuleiro();
        if(maqEstados.vencedor() == 1)
            System.out.println("[INFO] O vencedor do jogo foi: "+ maqEstados.getNome(jogA)+"\n");
        else if(maqEstados.vencedor() == 2)
            System.out.println("[INFO] O vencedor do jogo foi: "+ maqEstados.getNome(jogB)+"\n");
        else if(maqEstados.vencedor() == 0)
            System.out.println("[INFO] Houve um empate no jogo!\n");

        int cont = maqEstados.getTamanhoPasta("replays");
        if(maqEstados.getModoJogo() != 4) {
            maqEstados.gravarReplay("replay" + (cont + 1));
            System.out.println("[INFO] Replay do jogo gravado com sucesso!  [replay"+(cont+1)+"]\n");
        }

        int op = escolheOpcao("Voltar ao menu principal","Mostrar logs do jogo completo", "Sair");
        switch (op) {
            case 1:
                System.out.println();
                maqEstados.terminarJogo();
                break;
            case 2:
                System.out.println();
                mostrarLogCompleto();
                System.out.println();
                break;
            default:
                sair = true;
                break;
        }
    }

    private void menuJogadorHumano(int jogador){

        int op = escolheOpcao("Escolher coluna a jogar","Voltar a uma jogada anterior",
                "Jogar pe??a especial", "Gravar jogo\n", "Voltar ao menu principal");
        switch (op) {
            case 1:
                maqEstados.escolherColuna(escolherColuna());
                break;
            case 2:
                voltarAtras(jogador);
                break;
            case 3:
                if (maqEstados.getJogador(jogador).getPecaEspecial() > 0) { // se tiver peca especial
                    boolean pecaJogada = false;
                    while (!pecaJogada) {
                        int coluna;
                        do {
                            coluna = pedeInteiro("Coluna a jogar: ");
                            if (coluna < 1 || coluna > 7)
                                System.out.println("[INFO] Introduza uma op????o v??lida!");
                        } while (coluna < 1 || coluna > 7);

                        if (maqEstados.verificaColunaVazia(coluna))
                            System.out.println("[INFO] A coluna " + coluna + " j?? se encontra vazia. Selecione outra!");
                        else {
                            maqEstados.jogarPecaEspecial(coluna, jogador);
                            System.out.println("[INFO] Todas as pe??as da coluna " + coluna + " foram removidas!");
                            pecaJogada = true;
                        }
                    }
                } else
                    System.out.println("[INFO] De momento, n??o tem a pe??a especial!");
                break;
            case 4:
                String nomeFich = pedeString("Insira o nome do ficheiro a guardar o jogo: ");
                maqEstados.gravarJogo(nomeFich);
                System.out.println("[INFO] Jogo gravado com sucesso!");
                break;
            default:
                maqEstados.terminarJogo();
                break;
        }
    }

    private void menuJogadorAutomatico(int jogador){

        iuTabuleiro.mostraTabuleiro();
        int op = escolheOpcao(
                "Visualizar a peca jogada",
                "Voltar a uma jogada anterior",
                "Gravar jogo\n",
                "Voltar ao menu principal");
        switch (op) {
            case 1:
                maqEstados.escolherColuna(escolherColunaComputador());
                break;
            case 2:
                voltarAtras(jogador);
                break;
            case 3:
                 String nomeFich = pedeString("Insira o nome do ficheiro a guardar o jogo: ");
                 maqEstados.gravarJogo(nomeFich);
                 System.out.println("[INFO] Jogo gravado com sucesso!");
                break;
            default:
                System.out.println();
                maqEstados.terminarJogo();
                break;
        }
    }

    private void menuJogadorReplays(){

        iuTabuleiro.mostraTabuleiro();
        System.out.println();
        int op = escolheOpcao("Visualizar a peca jogada","Voltar ao menu principal");
        switch (op) {
            case 1:
                maqEstados.redo();
                maqEstados.replayJogada();
                break;
            default:
                System.out.println();
                maqEstados.terminarJogo();
                break;
        }
    }

    private void informacaoJogador(int jogador){

        System.out.println("\n--------------- Jogada " + maqEstados.getJogadas() + " ---------------");
        System.out.println("\n[INFO] ?? a vez do Jogador '" + maqEstados.getNome(jogador) + "'");
        System.out.println("\n | Jogadas A: "+maqEstados.getJogadasA()+" | Jogadas B: "+maqEstados.getJogadasB());
        System.out.println(" | Pe??a: '"+maqEstados.getPeca(jogador)+"'\n | Cr??ditos: "+maqEstados.getCreditosJogador(jogador));
    }

    private void miniJogoCalculosInfo(){
        System.out.println("Mini-Jogo: C??lculos Matem??ticos\n");
        System.out.println("Tem 30 segundos para acertar em pelo menos 5 c??lculos que lhe ir??o aparecer!");
        System.out.println("[INFO] Pressione qualquer tecla para come??ar...");
    }

    private void miniJogoPalavrasInfo(){
        System.out.println("Mini-Jogo: Palavras Aleat??rias\n");
        System.out.println("Tem um tempo limite para digitar corretamente as palavras que lhe ir??o aparecer!");
        System.out.println("[INFO] Pressione qualquer para come??ar...");
    }

    private int escolherColuna() {
        int coluna;
        do {
            coluna = pedeInteiro("Coluna a jogar: ");
            if (coluna < 1 || coluna > 7)
                System.out.println("[INFO] Introduza uma op????o v??lida!");
            if (maqEstados.getEstadoColuna(coluna))
                System.out.println("[INFO] A coluna escolhida j?? se encontra preenchida!");
        } while (coluna < 1 || coluna > 7 || maqEstados.getEstadoColuna(coluna));
        return coluna;
    }

    private int escolherColunaComputador() {
        int coluna;
        do {
            coluna = maqEstados.gerarColuna();
            System.out.println("Coluna jogada:" + coluna);
        } while (maqEstados.getEstadoColuna(coluna));
        return coluna;
    }

    private void voltarAtras(int jogador){
        int numVoltaAtras;
        if (maqEstados.getJogadas() - 1 == 0 ){
            System.out.println("\n[Info] N??o ?? poss??vel voltar atr??s na jogada inicial!");
            return;
        }
        if (maqEstados.getCreditosJogador(jogador) == 0){
            System.out.println("\n[Info] N??o tem cr??ditos suficientes para voltar atr??s!");
            return;
        }
        do {
            numVoltaAtras = pedeInteiro("\nDeseja voltar atr??s quantas jogadas?\nOp????o: ");
            if (numVoltaAtras < 1 || numVoltaAtras > 5)
                System.out.println("[Erro] Introduza uma op????o v??lida!");
            if (maqEstados.getCreditosJogador(jogador) < numVoltaAtras && numVoltaAtras <= 5)
                System.out.println("[Info] N??o tem cr??ditos suficientes para voltar atr??s " +numVoltaAtras+" jogada(s)!");
            if(maqEstados.getJogadas() - numVoltaAtras <= 0 && numVoltaAtras <= 5)
                System.out.println("[Erro] N??o ?? poss??vel voltar atr??s " +numVoltaAtras+" jogada(s)!");

        } while (maqEstados.getCreditosJogador(jogador) < numVoltaAtras || (numVoltaAtras < 1 || numVoltaAtras > 5)
                || (maqEstados.getJogadas() - numVoltaAtras) <= 0 );

        System.out.println("\n[INFO] Voltou a "+numVoltaAtras+" jogada(s) atr??s!");
        //voltar atras n jogadas
        maqEstados.voltarAtras(jogador, numVoltaAtras);
    }

    public int pedeInteiro(String pergunta) {
        System.out.print(pergunta);
        while (!sc.hasNextInt())
            sc.next();
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public String pedeString(String pergunta) {

        String resposta;
        do {
            System.out.print(pergunta);
            resposta = sc.nextLine().trim();
        } while (resposta.isEmpty());
        return resposta;
    }

    public  int escolheOpcao(String... opcoes) {
        int opcao;

        do {
            for (int i = 0; i < opcoes.length-1; i++)
                System.out.printf("%d. %s\n",i+1,opcoes[i]);
            System.out.printf("%d. %s\n",0,opcoes[opcoes.length-1]);
            opcao = pedeInteiro("\nOp????o: ");
            if(opcao < 0 || opcao>=opcoes.length)
                System.out.println("[INFO] Introduza uma opcao valida!\n");
        } while (opcao<0 || opcao>=opcoes.length);
        return opcao;
    }
}
*/