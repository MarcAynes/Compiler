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

    public boolean syntaxAnalysis(){
        int nivell = 0;
        char op = ' ';
        String newVar = "";
        boolean lastOne = false;
        int countOpened = 0;

        boolean ok = true;
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

                        else if(s != null && s.Type == 7){
                            op = 'S'; //selecció
                            nivell = 2;
                            break;
                        }

                        else if (s != null && s.Reserved_Word.equals("}")){
                            countOpened--;
                            break;
                        }

                        else { //estic fent una assignació
                            nivell = 1;
                            op = 'A';
                        }
                    case 1: //espero una variable
                        if(op == 'A' && taulaS.search(tokens.get(i)) != null || op == 'D'){ //miro que estigui a la taula de simbols
                            if(op == 'D'){
                                Symbol sym = taulaS.search(tokens.get(i));
                                if (sym != null && sym.Type != 0){
                                    nivell = -1;
                                    syntacticalError.addSyntacticError("No es pot declarar una variable amb el nom " + tokens.get(i));
                                    break;
                                }
                            }
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
                                //semantic.semanticAnalysis(taulaS, tokens, inici);
                                inici = i+1;
                            }

                        }
                        else if(op == 'A' && tokens.get(i).equals("=")){
                            nivell = 3;
                        }
                        else if(op == 'S' && tokens.get(i).equals("(")){
                            nivell = 3;
                        }
                        else if (op == 'S'){
                            syntacticalError.addSyntacticError("S'esperava rebre un (");
                            nivell = -1;
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
                        //només arribo aquí si és ASSIGNACIÓ/SELECCIÓ
                        Symbol sym = taulaS.search(tokens.get(i));
                        if(sym != null && sym.Type != 0){
                            nivell = -1;
                            syntacticalError.addSyntacticError("Paraula "+tokens.get(i)+" reservada");
                            break;
                        }
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
                                break;
                            }
                            limits[0] = 'a';
                            limits[1] = 'z';
                        }
                        else if (op == 'A'){
                            nivell = -1;
                            syntacticalError.addSyntacticError("No es pot assignar aquest valor a una variable");
                            break;
                        }
                        else{
                            nivell = -1;
                            syntacticalError.addSyntacticError("Valor de la comparació incorrecte");
                            break;
                        }

                        for (char c: ch) {
                            if (c > limits[1] || c < limits[0]){ //els valors rebuts surten del rang
                                if(c == '.'){
                                    if(!decimal){
                                        decimal = true;
                                        continue;
                                    }
                                    else{
                                        syntacticalError.addSyntacticError("Valors fora de rang");
                                        nivell = -1;
                                        break;
                                    }
                                }
                                nivell = -1;
                                syntacticalError.addSyntacticError("Valors fora de rang");
                            }
                        }
                        break;

                    case 4:
                        nivell = -1;
                        sym = taulaS.search(tokens.get(i));
                        if(op == 'A' && tokens.get(i).equals(";")){
                            System.out.println("OK");
                            nivell = 0;
                            op = ' ';
                            //semantic.semanticAnalysis(taulaS, tokens, inici);
                            inici = i+1;
                        }
                        else if(sym != null && sym.Type == 3){
                            nivell = 3;
                        }
                        else if(!lastOne && op == 'S' && sym != null && sym.Type == 8){
                            nivell = 3;
                            lastOne = true;
                        }
                        else if(lastOne && op == 'S' && sym != null && sym.Type == 9){
                            nivell = 5;
                            lastOne = false;
                        }
                        else if (op == 'A'){
                            syntacticalError.addSyntacticError("S'esperava ;");
                        }
                        else if (op == 'S' && lastOne){
                            syntacticalError.addSyntacticError("S'esperava )");
                        }
                        else if (op == 'S' && !lastOne){
                            syntacticalError.addSyntacticError("S'esperava un operador relacional");
                        }
                        break;
                    case 5:
                        if(op == 'S' && tokens.get(i).equals("{")){
                            System.out.println("OK");
                            nivell = 0;
                            op = ' ';
                            countOpened++;
                        }
                        else{
                            syntacticalError.addSyntacticError("S'esperava {");
                        }
                }

                if(nivell < 0){
                    syntacticalError.mostrarErrors();
                    ok = false;
                    break;
                }
            }

            if(nivell > 0){ //detecta errors a l'ultima linia
                syntacticalError.addSyntacticError("S'esperava ;");
                syntacticalError.mostrarErrors();
                ok = false;
            }
            else if (nivell == 0 && countOpened > 0){
                ok = false;
                syntacticalError.addSyntacticError("S'esperava }");
                syntacticalError.mostrarErrors();
            }
        }
        ok = ok & semantic.Errors(); //mostrar errors semantic
        return ok;
    }
}
