package jogo.logica.memento;


import java.io.IOException;

public interface IMementoOriginator {
    Memento getMemento() throws IOException; // //guardar
    void setMemento(Memento m) throws IOException, ClassNotFoundException;  // restaurar

}

