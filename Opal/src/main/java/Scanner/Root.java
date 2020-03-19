package Scanner;

import java.io.PrintWriter;

public class Root implements trieInt{

    public Node[] lletres = new Node[255];

    public Node pare;
    public int altura;

    public Root() {
        pare = null;
        altura = 1;


        //for (int i = 0; i < lletres.length; i++) {
        //  lletres = null;
        //}
    }

    public void add(char[] paraula){
        //numero
        if (lletres[paraula[0]] == null){
            lletres[paraula[0]] = new Node();
        }
        lletres[paraula[0]].addLetter(paraula, 1, this);

    }

    public Return search(char[] paraula, Return retorna){
        //numero
        if (lletres[paraula[0]] != null){
            retorna = lletres[paraula[0]].search(paraula, 0, retorna);
        }
        return retorna;
    }

    public int compara (char[] frase, int index){

        if (lletres[frase[index]] != null){

            return lletres[frase[index]].compara(frase, index);
        }

        return index;

    }

    public void eliminaNode(char[] paraula){

        lletres[paraula[0]].elimina(paraula, 0);

    }

    public Node getPare() {
        return pare;
    }

    public void setPare(Node pare) {
        this.pare = pare;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public Node[] getLletres() {
        return lletres;
    }

    public void setLletres(Node[] lletres) {
        this.lletres = lletres;
    }

    @Override
    public void EliminaFill(Node fill) {
        for (int i = 0; 255 > i; i++) {
            if (lletres[i] != null && lletres[i].equals(fill)) {
                lletres[i] = null;
                break;
            }
        }
    }
}
