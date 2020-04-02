package Parser;

import ErrorHandler.SyntacticalError;
import ErrorHandler.ErrorHandler;
import Scanner.Scanner;
import Semantic.Semantic;
import TaulaDeSimbols.Symbol;
import TaulaDeSimbols.SymbolTable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private Scanner scanner;
    private SymbolTable taulaS;
    private SyntacticalError syntacticalError;
    Semantic semantic = new Semantic();

    public Parser(SymbolTable taulaS, Scanner scanner){
        this.scanner = scanner;
        this.taulaS = taulaS;
        this.syntacticalError = new SyntacticalError();
    }

    public void syntaxAnalysis(){
        int nivell = 0;
        char op = ' ';
        String newVar = "";
        //llança l'Scanner i obté una llista de Tokens
        List<String> tokens = scanner.getTokens();

        if (tokens != null){
            int inici = 0;
            for (int i = 0; tokens.size() > i; i++) {
                switch (nivell){
                    case 0:
                        Symbol s = taulaS.search(tokens.get(i));
                        if(s != null && s.Type == 4){ //miro que estigui a la taula de simbols i sigui una declaracio
                            //afegir D i tV a l'arbre
                            nivell = 1;
                            op = 'D'; //equival a afegir D a l'arbre
                            break;
                        }

                        else { //estic fent una assignació
                            nivell = 1;
                            op = 'A';
                        }
                    case 1: //espero una variable
                        if(op == 'A' && taulaS.search(tokens.get(i)) != null || op == 'D'){ //miro que estigui a la taula de simbols
                            nivell = 2;

                            newVar = tokens.get(i);

                            //separo per lletres
                            char[] ch = tokens.get(i).toCharArray();

                            //miro que tots els caràcters siguin lletres
                            for (char lletra: ch) {
                                if(!Character.isLetter(lletra)){
                                    nivell = -1;
                                    break;
                                }
                            }
                        }
                        else{
                            nivell = -1;
                            syntacticalError.addSyntacticError("Variable '" + tokens.get(i) + "' no declarada");
                        }
                        break;
                    case 2:
                        if(op == 'D' && tokens.get(i).equals(";")){
                            System.out.println("OK");
                            nivell = 0;
                            op = ' ';
                            //afegim la variable a la taula de símbols
                            if(!taulaS.add(new Symbol(newVar, 0))){
                                nivell = -1;
                                syntacticalError.addSyntacticError("Ja existeix la variable '"+ newVar + "' a la taula de símbols");
                            }else{
                                semantic.semanticAnalysis(taulaS, tokens, inici);
                                inici = i+1;
                            }

                        }
                        else if(op == 'A' && tokens.get(i).equals("=")){
                            nivell = 3;
                        }
                        else{
                            syntacticalError.addSyntacticError("S'esperava rebre un = o un ;");
                            nivell = -1;
                        }
                        break;
                    case 3:
                        boolean decimal = false;
                        nivell = 4;
                        char[] limits = {0, 0};
                        //només arribo aquí si és ASSIGNACIÓ
                        //separo per lletres
                        char[] ch = tokens.get(i).toCharArray();

                        if(ch[0] == '\'' && ch[2] == '\'' && ch.length == 3 && (Character.isLetter(ch[1]) || Character.isDigit(ch[1]))){ //rebo un char en format 'a'
                            break;
                        }
                        if(Character.isDigit(tokens.get(i).charAt(0))){ //rebo un int
                            limits[0] = '0';
                            limits[1] = '9';
                        }
                        else if(Character.isLetter(tokens.get(i).charAt(0))){ //rebo una variable
                            if(taulaS.search(tokens.get(i)) == null){
                                nivell = -1;
                                syntacticalError.addSyntacticError("Variable '" + tokens.get(i) + "' no declarada.");
                            }
                            limits[0] = 'a';
                            limits[1] = 'z';
                        }
                        else{
                            nivell = -1;
                            syntacticalError.addSyntacticError("No es pot assignar aquest valor a una variable");
                        }

                        for (char c: ch) {
                            if (c > limits[1] || c < limits[0]){ //els valors rebuts surten del rang
                                if(c == '.'){
                                    if(!decimal){
                                        decimal = true;
                                        continue;
                                    }
                                    else{
                                        syntacticalError.addSyntacticError("Valors fora de rang al assignar");
                                        nivell = -1;
                                        break;
                                    }
                                }
                                nivell = -1;
                                syntacticalError.addSyntacticError("Valors fora de rang al assignar");
                            }
                        }
                        break;

                    case 4:
                        Symbol sym = taulaS.search(tokens.get(i));
                        if(op == 'A' && tokens.get(i).equals(";")){
                            System.out.println("OK");
                            nivell = 0;
                            op = ' ';
                            semantic.semanticAnalysis(taulaS, tokens, inici);
                            inici = i+1;
                        }
                        else if(op == 'A' && sym != null && sym.Type == 3){
                            nivell = 3;
                        }
                        else{
                            nivell = -1;
                            syntacticalError.addSyntacticError("S'esperava ;");
                        }
                        break;
                }

                if(nivell < 0){
                    syntacticalError.mostrarErrors();
                    break;
                }
            }

            if(nivell > 0){ //detecta errors a l'ultima linia
                syntacticalError.addSyntacticError("S'esperava ;");
                System.out.println("S'esperava ;");
            }
        }
        semantic.Errors(); //mostrar errors semantic
    }
}
