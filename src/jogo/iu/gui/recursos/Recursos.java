package jogo.iu.gui.recursos;

import java.io.InputStream;

public class Recursos  {
    private Recursos() {}
    public static InputStream getResourceFileAsStream(String name){
        return Recursos.class.getResourceAsStream(name);
    }
    public static String getResourceFilename(String name){
        return Recursos.class.getResource(name).toExternalForm();
    }
}

