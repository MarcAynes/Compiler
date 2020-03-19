package Parser;

import Scanner.Scanner;
import Scanner.Token;
import TaulaDeSimbols.SymbolTable;

import java.util.List;

public class Parser {
    private Scanner scanner;
    private SymbolTable taulaS;

    public Parser(SymbolTable taulaS){
        this.scanner = new Scanner();
        this.taulaS = taulaS;
    }

    public void syntaxAnalysis(String codi){

        //llança l'Scanner i obté una llista de Tokens
        List<Token> tokens = scanner.getTokens(codi);

        if (tokens != null){
            for (Token token: tokens) {
                taulaS.search(token.valor); //que retorna si no hi es?
            }
        }
    }

}
