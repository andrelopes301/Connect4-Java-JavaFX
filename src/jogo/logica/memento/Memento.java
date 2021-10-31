package jogo.logica.memento;

import java.io.*;


public class Memento implements Serializable { //Memento genérico que dá para tudo quando se recorre a serialização ...
    private byte[] snapshot = null;

    public Memento(Object obj) throws IOException {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;

        try {
            baos = new ByteArrayOutputStream(); // os bytes vao ser guardados num array de bytes em memoria
            oos = new ObjectOutputStream(baos);

            oos.writeObject(obj); // guardar uma imagem do objeto em memoria
            snapshot = baos.toByteArray(); // buscar o array de bytes

        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }


    //desserialização
    public Object getSnapshot() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        if (snapshot == null)
            return null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(snapshot)); // buscar o objeto guardado no array de bytes

            return ois.readObject();
        } finally {
            if(ois!=null){
                ois.close();
            }
        }
    }



}
