
import Adresscode.GeneratorTAC;
import Adresscode.Separacio;
import Optimization.Optimizer;
import Parser.Parser;
import Scanner.Scanner;
import TaulaDeSimbols.SymbolTable;
import MIPS.MIPS;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

public class Main {

    static SymbolTable symbolTable = new SymbolTable("src/Symbols.json");

    public static void main(String[] args) {
        for (int i = 1; i <= 14; i++) {
            boolean ok;
            Scanner scanner = new Scanner("src/tests/test" + i /*args[0]*/);
            System.out.println("****************** test" + i+ " ************************");
            Parser parser = new Parser(symbolTable, scanner);

            GeneratorTAC generatorTAC = new GeneratorTAC();
            Optimizer optimizer = new Optimizer();

            ok = parser.syntaxAnalysis();
            if (!ok) {
                symbolTable = new SymbolTable("src/Symbols.json");
               // continue;
            }
            scanner = new Scanner("src/tests/test" + i /*args[0]*/);
            Separacio separacio = new Separacio(scanner);
            separacio.separar();
            optimizer.optimization(generatorTAC.generator(separacio, i), i);
            symbolTable = new SymbolTable("src/Symbols.json");

            MIPS mips = new MIPS(i);
        }
    }
}
