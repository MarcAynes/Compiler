package Scanner;

public class Trie {
    Root root;

    public Trie(){

        Root root = new Root();
        this.root = root;
    }

    public void add(char[] paraula){

        root.add(paraula);
    }

    public Return search(char[] paraula){
        Return retorna = new Return();
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

