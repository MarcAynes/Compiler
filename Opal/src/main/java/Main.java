import Parser.Parser;
import TaulaDeSimbols.SymbolTable;

public class Main {
    public static void main(String[] args) {
        String codi;

        SymbolTable symbolTable = new SymbolTable();
        Parser parser = new Parser(symbolTable);

        //llegir fitxer codi i passar-ho a string
        codi = "int a;"; //codi prova

        //inicar Parser
        parser.syntaxAnalysis(codi);
    }
}
