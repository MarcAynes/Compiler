package MIPS.Translate;

import MIPS.SymbolTable.Symbol;
import MIPS.SymbolTable.SymbolTable;
import MIPS.Scanner.mipsScanner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Translate {

    SymbolTable table;
    mipsScanner scan;
    List<String> tokens;

    public Translate(SymbolTable taulaS, mipsScanner scanner){
        this.scan = scanner;
        this.table = taulaS;
        this.tokens = scanner.getTokens();
    }

    public boolean letsGoo(File file) throws IOException{
        List<String> auxTokens = new ArrayList<>();
        BufferedWriter output = null;
        int totalBytes = 0;

        output = new BufferedWriter(new FileWriter(file));

        if (tokens != null && output != null){



            output.write(".text\n");
            output.write(".globl main\n");
            output.write("main:\n");
            output.write("move $fp, $sp\n");


            for (int i = 0; i < tokens.size(); i++){

                if (tokens.get(i).equals("if")){
                    auxTokens.add(tokens.get(i));
                    i+= 2;

                    if (tokens.get(i).equals("!")){
                        //negar condicio
                        i++;
                    }


                 continue;
                }

                //asignation

                if (tokens.get(i+1).equals("=")){
                    Symbol s = table.search(tokens.get(i));

                    if (s == null){ //new variable
                        s = new Symbol(tokens.get(i), 0, -1);
                        i += 2;
                        s.position = totalBytes;

                        if (tokens.get(i+1).equals(";")){   //simple asignation, only with one element, can be number, char, or variable

                            if (isInt(tokens.get(i)) || isFloat(tokens.get(i))){
                                //is number
                                totalBytes += 4;
                                s.actualType = "int";                                               //int type = int || float
                                table.add(s);

                                output.write("sub $sp, $sp, 4\n");
                                output.write("li $t0, " + tokens.get(i) + "\n");
                                output.write("sw, $t0, " + s.position + "($fp)\n");

                                i++;        //move pointer of the string to the ;
                                continue;

                            }

                            if (isChar(tokens.get(i))){

                                totalBytes += 1;
                                s.actualType = "char";
                                table.add(s);

                                output.write("sub $sp, $sp, 1\n");
                                output.write("li $t0, " + tokens.get(i) + "\n");
                                output.write("sw, $t0, " + s.position + "($fp)\n");

                                i++;        //move pointer of the string to the ;
                                continue;
                            }

                            //variable asignation
                            totalBytes += table.search(tokens.get(i)).actualType.equals("char") ? 1 : 4;
                            s.actualType = table.search(tokens.get(i)).actualType;
                            table.add(s);

                            output.write("sub $sp, $sp, " + (table.search(tokens.get(i)).actualType.equals("char") ? 1 : 4) + "\n");
                            output.write("move " + s.position + "($fp), " + table.search(tokens.get(i)).position + "($fp)\n");

                            i++;        //move pointer of the string to the ;
                            continue;

                        }

                        if (tokens.get(i+1).equals("+") || tokens.get(i+1).equals("-")){
                            //declaration of variable with operation, this operation can be +, - at this moment, but in the future could be comparator
                            boolean integer = false;
                            s.position = totalBytes;
                            totalBytes += 1;

                            //at least one of the 2 variables have to be int or float to asign 4 bytes of memory if not
                            if ((table.search(tokens.get(i)) != null && table.search(tokens.get(i)).actualType.equals("int")) || isFloat(tokens.get(i)) || isInt(tokens.get(i)) || isFloat(tokens.get(i+2)) || isInt(tokens.get(i+2))
                                    || (table.search(tokens.get(i)) != null && table.search(tokens.get(i+2)).actualType.equals("int"))){
                                integer = true;
                                totalBytes += 3;
                            }

                            output.write("sub $sp, $sp, " + (integer ? 4 : 1) + "\n");

                            s.actualType = "int";
                            table.add(s);

                            String aux = "";
                            aux += tokens.get(i+1).equals("-") ? "sub $t0, " : "add $t0, ";

                            if (table.search(tokens.get(i)) != null){       //if tis a variablem store de data
                                output.write("lw $t0, " + table.search(tokens.get(i)).position + "($fp)\n");
                            }else{
                                output.write("li $t0, " + tokens.get(i) + "\n");
                            }

                            if (table.search(tokens.get(i+2)) != null){
                                output.write("lw $t1, " + table.search(tokens.get(i+2)).position + "($fp)\n");
                            }else{
                                output.write("li $t1, " + tokens.get(i+2) + "\n");
                            }

                            aux += "$t0, $t1\n";
                            output.write(aux);

                            output.write("sw $t0, " + s.position + "$(fp)\n");

                            i += 3;     //move pointer of the string to the ;

                            continue;

                        }

                        //TODO: declaration of variable with comparation, ex: T1 = T2 == T3; It could be a label too.

                    }

                    if (tokens.get(i+3).equals(";") || tokens.get(i+3).equals("+") || tokens.get(i+3).equals("-")) {    //its asignation

                        i += 2;             //move string pointer to the first variable/literal after =
                        if (tokens.get(i+1).equals("+") || tokens.get(i+1).equals("-")) {
                            if (table.search(tokens.get(i)) != null) {
                                output.write("lw $t0, " + table.search(tokens.get(i)).position + "($fp)\n");
                            } else {
                                output.write("li $t0, " + tokens.get(i) + "\n");
                            }

                            if (table.search(tokens.get(i+2)) != null){
                                output.write("lw $t1, " + table.search(tokens.get(i+2)).position + "($fp)\n");
                            }else{
                                output.write("li $t1, " + tokens.get(i+2) + "\n");
                            }

                            output.write(tokens.get(i+1).equals("-") ? "sub $t0, $t0, $t1\n" : "add $t0, $t0, $t1\n");
                            output.write("sw $t0, " + s.position + "$(fp)\n");

                            i += 3;
                            continue;

                        }

                        if (table.search(tokens.get(i)) != null) {

                            output.write("move "  + s.position + "($fp), " + table.search(tokens.get(i)).position + "($fp)\n");
                        } else {
                            output.write("li $t0, " + tokens.get(i) + "\n");
                            output.write("sw, $t0, " + s.position + "($fp)\n");
                        }
                        i++;
                        continue;
                    }


                }
            }
        }

        return true;
    }

    private boolean isInt(String possibleNumber){
        try {

            int i = Integer.parseInt(possibleNumber);
            return true;
        }catch (NumberFormatException e){

            return false;
        }
    }

    private boolean isFloat(String possibleNumber){
        try {

            float i = Float.parseFloat(possibleNumber);
            return true;
        }catch (NumberFormatException e){

            return false;
        }
    }

    private boolean isChar(String possibleChar){
        char[] ch = possibleChar.toCharArray();
        if (ch[0] == '\''){

            return true;
        }

        return false;
    }

    private char getMeMyChar(String charecter){
        char[] ch = charecter.toCharArray();
        return ch[1];
    }
}
