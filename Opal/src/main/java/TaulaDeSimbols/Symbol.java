package TaulaDeSimbols;

public class Symbol {

    public String Reserved_Word;
    public int Type;

    /*
    0 - variable
    1 - final de linia
    2 - assignacio
    3 - operacio
    4 - declaracio/tipus
    5 - funcio

     */

    public Symbol(String reserved_Word, int Type){
        this.Reserved_Word = reserved_Word;
        this.Type = Type;
    }


}