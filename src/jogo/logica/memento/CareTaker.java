package jogo.logica.memento;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker implements Serializable {
    private final IMementoOriginator originator;

    private Deque<Memento> stackHistorico = new ArrayDeque<>();
    private Deque<Memento> stackRedo = new ArrayDeque<>();

    public CareTaker(IMementoOriginator org) {
        this.originator = org;
    }

    public void gravaMemento() {
        stackRedo.clear();
        try{
            stackHistorico.push(originator.getMemento());
        } catch(IOException ex) {
            System.err.println("gravaMemento: " + ex);
            stackHistorico.clear();
            stackRedo.clear();
        }
    }


    public void reiniciar(){

        int tamStackHistorico =stackHistorico.size();

        for(int i = 0; i < tamStackHistorico ; i++)
            undo();
    }

    public void undo() {
        if (stackHistorico.isEmpty()) {
            return;
        }

        try {
            Memento atual = originator.getMemento();
            stackRedo.push(atual);
            Memento anterior = stackHistorico.pop();
            originator.setMemento(anterior);
        } catch(IOException | ClassNotFoundException ex) {
            System.err.println("undo: " + ex);
            stackHistorico.clear();
            stackRedo.clear();
        }

    }

    public void redo() {
        if (stackRedo.isEmpty()) {
            return;
        }
        try {
            Memento sredo = stackRedo.pop();
            stackHistorico.push(originator.getMemento());
            originator.setMemento(sredo);
        } catch(IOException | ClassNotFoundException ex) {
            System.err.println("redo: " + ex);
            stackHistorico.clear();
            stackRedo.clear();
        }
    }


    public void clear() {
        stackHistorico.clear();
        stackRedo.clear();
    }
}