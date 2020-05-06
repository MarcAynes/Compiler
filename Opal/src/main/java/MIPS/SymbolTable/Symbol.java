package MIPS.SymbolTable;

public class Symbol {

    public String Reserved_Word;
    public int Type;
    public int position;
    public String actualType = "";              //int = int || float

    /*
    0 - variable
    1 - assignacio
    2 - operacio
    3 - comparacio
    4 - seleccio (if)
    5 - parentesis
    6 - negacio

    7 - salt (goto)
    8 - final (;)
    9 - label

     */

    public Symbol(String reserved_Word, int Type, int position){
        this.Reserved_Word = reserved_Word;
        this.Type = Type;
        this.position = position;
    }
}