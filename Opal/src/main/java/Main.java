import Parser.Parser;
import Scanner.Scanner;
import TaulaDeSimbols.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //System.out.println(args[0]);
        SymbolTable taulaS = new SymbolTable("src/Symbols.json");
        Scanner scanner = new Scanner("src/codi");
        Parser parser = new Parser(taulaS, scanner);

        parser.syntaxAnalysis();
    }
}
