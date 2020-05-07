package MIPS.Scanner;

import MIPS.Scanner.Node;
import MIPS.Scanner.Return;
import MIPS.Scanner.trieIntMips;

public class Root implements trieIntMips {

    public MIPS.Scanner.Node[] lletres = new MIPS.Scanner.Node[255];

    public MIPS.Scanner.Node pare;
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
            lletres[paraula[0]] = new MIPS.Scanner.Node();
        }
        lletres[paraula[0]].addLetter(paraula, 1, this);

    }

    public MIPS.Scanner.Return search(char[] paraula, Return retorna){
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

    public MIPS.Scanner.Node getPare() {
        return pare;
    }

    public void setPare(MIPS.Scanner.Node pare) {
        this.pare = pare;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public MIPS.Scanner.Node[] getLletres() {
        return lletres;
    }

    public void setLletres(MIPS.Scanner.Node[] lletres) {
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
