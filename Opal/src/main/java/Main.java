
import Adresscode.GeneratorTAC;
import Adresscode.Separacio;
import Optimization.Optimizer;
import Parser.Parser;
import Scanner.Scanner;
import TaulaDeSimbols.SymbolTable;


public class Main {

    static SymbolTable symbolTable = new SymbolTable("src/Symbols.json");

    public static void main(String[] args) {
        //System.out.println(args[0]);
        Scanner scanner = new Scanner("src/tests/" + args[0]);
        Parser parser = new Parser(symbolTable, scanner);

        GeneratorTAC generatorTAC = new GeneratorTAC();
        Optimizer optimizer = new Optimizer();

        parser.syntaxAnalysis();
        scanner = new Scanner("src/tests/" + args[0]);
        Separacio separacio=new Separacio(scanner);
        separacio.separar();
        optimizer.optimization(generatorTAC.generator(separacio));
    }
}
