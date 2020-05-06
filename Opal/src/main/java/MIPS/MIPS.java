package MIPS;

import MIPS.Scanner.mipsScanner;
import MIPS.SymbolTable.SymbolTable;
import MIPS.Translate.Translate;

import java.io.File;
import java.io.IOException;

public class MIPS {

    SymbolTable symbolTable = new SymbolTable("src/main/java/MIPS/Scanner/Symbols.json");

    public MIPS(int i){
        File file = new File("src/MIPSAssembly/MIPS"+ i);
        mipsScanner scanner = new mipsScanner("src/GeneratedTAC/TAC" + i /*args[0]*/);
        Translate translate = new Translate(symbolTable, scanner);
        try {
            translate.letsGoo(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
