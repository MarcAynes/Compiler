package Scanner;

public class Return {

    public int nombre = 0;
    public char[][] frases;

    public Return(){
        this.nombre = 0;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public char[][] getFrases() {
        return frases;
    }

    public void setFrases(char[][] frases) {
        this.frases = frases;
    }
}