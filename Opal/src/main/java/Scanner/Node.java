package Scanner;

import java.io.PrintWriter;

public class Node implements trieInt {
    public Object pare;
    public int altura;

    public char lletra;
    public int value;

    public Node[] lletres = new Node[255];


    public Node() {
        altura = 1;
        value = 0;

        for (int i = 0; i < lletres.length; i++) {
            lletres[i] = null;
        }
    }

    public void addLetter(char[] lletra, int i, Object aiudame) {
        altura = altura < lletra.length - i ? lletra.length - i : altura;

        this.lletra = lletra[i - 1];
        pare = aiudame;
        if (lletra.length == i) {
            //cas trivial
            value++;

        } else {

            //paraula
            if (lletres[lletra[i]] == null) {
                lletres[lletra[i]] = new Node();
            }
            lletres[lletra[i]].addLetter(lletra, i + 1, this);
        }

    }

    //paraula: array que ens han introduit
    //nombre: nombre de paraules que hem retornat
    //posicio: posicioactual de la array
    public Return search(char[] paraula, int posicio, Return retorna) {
        if (paraula.length > posicio + 1 && lletres[paraula[posicio + 1]] != null) {

            retorna = lletres[paraula[posicio + 1]].search(paraula, posicio + 1, retorna);

        } else {

            if (value >= 1) {
                retorna.frases = new char[1][];
                retorna.frases[0] = paraula;
                retorna.nombre = 1;
            }
        }
        return retorna;
    }


    public int compara(char[] frase, int index){

        if (frase.length > index + 1 && lletres[frase[index + 1]] != null) {

            return lletres[frase[index + 1]].compara(frase, index + 1);

        } else {

            if (value >= 1 ) {

                return index;
            }
        }
        return -1;

    }

    public Boolean elimina(char[] paraula, int posicio) {
        //ens posicionem a la ultima lletra de la paraula recursivament
        boolean owisim = true;
        if (paraula.length > posicio + 1) {

            if (lletres[paraula[posicio + 1]] != null) {
                owisim = lletres[paraula[posicio + 1]].elimina(paraula, posicio + 1);
            }

        }
        if  (owisim) {
            //si som a la ultima lletra de la paraula posem el valor a 0
            if (paraula.length == posicio + 1) {
                value = 0;
            }

            boolean owo = false;
            for (int i = 0; 255 > i; i++) {
                if (lletres[i] != null) {
                    owo = true;
                    break;
                }
            }
            if (!owo) {
                if (pare instanceof trieInt) {
                    ((trieInt) pare).EliminaFill(this);
                }
            }
        }
        return owisim;

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


    public Object getPare() {
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

    public char getLletra() {
        return lletra;
    }

    public void setLletra(char lletra) {
        this.lletra = lletra;
    }

}

