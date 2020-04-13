
import Adresscode.GeneratorTAC;
import Adresscode.Separacio;
import Optimization.Optimizer;
import Parser.Parser;
import Scanner.Scanner;
import TaulaDeSimbols.SymbolTable;

public class Main {

    static SymbolTable symbolTable = new SymbolTable("src/Symbols.json");

    public static void main(String[] args) {
        boolean ok;
        Scanner scanner = new Scanner("src/tests/" + args[0]);
        Parser parser = new Parser(symbolTable, scanner);

        GeneratorTAC generatorTAC = new GeneratorTAC();
        Optimizer optimizer = new Optimizer();

        ok = parser.syntaxAnalysis();
        if (!ok){
            return;
        }
        scanner = new Scanner("src/tests/" + args[0]);
        Separacio separacio= new Separacio(scanner);
        separacio.separar();
        for (int i = 0; i<separacio.operacions.size(); i++)
        {
            System.out.println(separacio.operacions.get(i).getArg1() + " " + separacio.operacions.get(i).getOp() + " OO" + separacio.operacions.get(i).getArg2());
        }
        optimizer.optimization(generatorTAC.generator(separacio));
    }
}
