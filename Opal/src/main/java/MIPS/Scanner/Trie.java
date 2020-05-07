package MIPS.Scanner;

import MIPS.Scanner.Return;
import MIPS.Scanner.Root;

public class Trie {
    MIPS.Scanner.Root root;

    public Trie(){

        MIPS.Scanner.Root root = new Root();
        this.root = root;
    }

    public void add(char[] paraula){

        root.add(paraula);
    }

    public MIPS.Scanner.Return search(char[] paraula){
        MIPS.Scanner.Return retorna = new Return();
        retorna.nombre = -1;
        retorna = root.search(paraula, retorna);
        return retorna;
    }

    public int compara(char[] frase, int index){

        return root.compara(frase, index);
    }

    public void eliminarParaula(char[] paraula){
        root.eliminaNode(paraula);
    }
}
