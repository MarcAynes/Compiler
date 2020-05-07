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
            output.write(".data\n");
            output.write(".globl main\n");
            output.write("main:\n");
            output.write("move $fp, $sp\n");

            for (int i = 0; i < tokens.size(); i++){        // pepe = pepe + 1; i = 0
                if(tokens.get(i).startsWith("Fiif") || tokens.get(i).startsWith("Fiwhile") || tokens.get(i).startsWith("loop")){
                    output.write("$"+tokens.get(i) + ":\n");
                    continue;
                }
                if(tokens.get(i).equals("goto")){
                    i++; //vaig al label
                    output.write("b $" +tokens.get(i) + "\n");
                    continue;
                }
                if (tokens.get(i).equals("if")){    //if(!t1) i = 0; i -> "if"
                    auxTokens.add(tokens.get(i));
                    i+= 2;                          //i = 2; i -> "!"

                    String aux = "beq ";
                    if (tokens.get(i).equals("!")){
                        //negar condicio
                        i++;
                        aux = "bne ";
                    }

                    if (table.search(tokens.get(i)) != null){       //if tis a variablem store de data
                        output.write("lw $t0, " + table.search(tokens.get(i)).position + "($fp)\n");
                    }else{
                        output.write("li $t0, " + tokens.get(i) + "\n");
                    }

                    output.write("li $t1,0\n");

                    i+=3; //vaig al label
                    aux += "$t0, $t1, $" + tokens.get(i) + "\n";
                    output.write(aux);

                    i++; //vaig al ;
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
                                output.write("sb, $t0, " + s.position + "($fp)\n");

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
                            s.actualType = "char";

                            //at least one of the 2 variables have to be int or float to asign 4 bytes of memory if not
                            if ((table.search(tokens.get(i)) != null && table.search(tokens.get(i)).actualType.equals("int")) || isFloat(tokens.get(i)) || isInt(tokens.get(i)) || isFloat(tokens.get(i+2)) || isInt(tokens.get(i+2))
                                    || (table.search(tokens.get(i)) != null && table.search(tokens.get(i+2)).actualType.equals("int"))){
                                integer = true;
                                totalBytes += 3;
                                s.actualType = "int";
                            }

                            output.write("sub $sp, $sp, " + (integer ? 4 : 1) + "\n");

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

                            if (s.actualType.equals("int")) {
                                output.write("sw $t0, " + s.position + "$(fp)\n");
                            }else{
                                output.write("sb $t0, " + s.position + "$(fp)\n");
                            }

                            i += 3;     //move pointer of the string to the ;

                            continue;

                        }
                        if (tokens.get(i).equals("==") || tokens.get(i).equals("!=") || tokens.get(i+1).equals("<") || tokens.get(i+1).equals("<=") || tokens.get(i+1).equals(">") || tokens.get(i+1).equals(">=")){
                            s.position = totalBytes;
                            totalBytes += 1;

                            output.write("sub $sp, $sp, " + 1 + "\n");

                            s.actualType = "char";
                            table.add(s);

                            //guardo els valors de la comparació
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

                            //busco el resultat de la comparació
                            i+=1;
                            String aux = "";

                            if(tokens.get(i).equals("==") || tokens.get(i).equals("!=")){
                                aux += "xor $t2,$t0,$t1\n";
                                switch (tokens.get(i)){
                                    case "==":
                                        aux += "sltu $t2,$t2,1\n";
                                        break;
                                    case "!=":
                                        aux += "li $t4,0\n";
                                        aux += "sltu $t2,$t4,$t2\n";
                                        break;
                                }
                                aux += "andi $t2,$t2,0x00ff\n";
                            }
                            else {
                                aux += "slt $t2, ";
                                switch (tokens.get(i)) {
                                    case "<":
                                        aux += "$t0, $t1\n";
                                        break;
                                    case "<=":
                                        aux += "$t1, $t0\n";
                                        aux += "xori $t2, $t2, 0x1\n";
                                        break;
                                    case ">":
                                        aux += "$t1, $t0\n";
                                        break;
                                    case ">=":
                                        aux += "$t0, $t1\n";
                                        aux += "xori $t2, $t2, 0x1\n";
                                        break;

                                }
                                aux += "andi $t2, $t2, 0x00ff\n";
                            }

                            output.write(aux);
                            output.write("sw $t2, " + s.position + "$(fp)\n");
                            i += 2; //vaig al ;
                            continue;
                        }
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
        output.close();

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
