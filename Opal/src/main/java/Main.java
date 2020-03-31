
import Parser.Parser;
import Scanner.Scanner;
import TaulaDeSimbols.SymbolTable;


public class Main {

    static SymbolTable symbolTable = new SymbolTable("src/Symbols.json");

    public static void main(String[] args) {
        //System.out.println(args[0]);
        Scanner scanner = new Scanner("src/tests/test6");
        Parser parser = new Parser(symbolTable, scanner);

        parser.syntaxAnalysis();
    }
}
