package Semantic;

import ErrorHandler.SemanticalError;
import TaulaDeSimbols.Symbol;
import TaulaDeSimbols.SymbolTable;

import java.util.HashMap;
import java.util.List;

public class Semantic {
    HashMap<String, Tipus> typeTable = new HashMap<String, Tipus>();
    SemanticalError sm = new SemanticalError();

    public void semanticAnalysis(List<SymbolTable> symbols, List<String> tokens, int posicio_inici){


        Symbol s = searchInTables(tokens.get(posicio_inici), symbols);
        if (s.Type == 0){       // var

            Tipus t = typeTable.get(s.Reserved_Word);
            if (t.varType.equals("int")){

                for(int i = posicio_inici+1; !tokens.get(i).equals(";"); i++){
                    s = searchInTables(tokens.get(posicio_inici), symbols);
                    //System.out.println(tokens.get(i));
                    if (s != null && s.Type == 0 && !(typeTable.get(s.Reserved_Word).varType.equals("int") || typeTable.get(s.Reserved_Word).varType.equals("char"))){

                        sm.addSemanticalError("Integer can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                    }else{
                        if (tokens.get(i).contains(".")){

                            //throw new TypeException("Integer can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            sm.addSemanticalError("Integer can not be assigned to float");
                            break;
                        }
                    }
                }

            }else{
                if(t.varType.equals("char")){

                    for(int i = posicio_inici+1; !tokens.get(i).equals(";"); i++){

                        s = searchInTables(tokens.get(posicio_inici), symbols);
                        //System.out.println(tokens.get(i));
                        if (s != null && s.Type == 0 && !(typeTable.get(s.Reserved_Word).varType.equals("char") || typeTable.get(s.Reserved_Word).varType.equals("int"))){

                            //throw new TypeException("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            sm.addSemanticalError("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            break;
                        }else{
                            if (tokens.get(i).contains(".")){

                                //throw new TypeException("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                                sm.addSemanticalError("char can not be assigned to float");
                                break;
                            }
                        }
                    }

                }else{
                    if (t.varType.equals("float")){
                        for(int i = posicio_inici+1; !tokens.get(i).equals(";"); i++){

                            s = searchInTables(tokens.get(posicio_inici), symbols);
                            //System.out.println(tokens.get(i));
                            if (s != null && s.Type == 0 && !(typeTable.get(s.Reserved_Word).varType.equals("char") || typeTable.get(s.Reserved_Word).varType.equals("int") || typeTable.get(s.Reserved_Word).varType.equals("float"))){

                                //throw new TypeException("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                                sm.addSemanticalError("float can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                                break;
                            }else{
                                if (tokens.get(i).contains("'")){

                                    //throw new TypeException("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                                    sm.addSemanticalError("float can not be assigned to char");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else{

            if (s.Type == 4){           //declaration
                if (s.Reserved_Word.equals("int")) {

                    s = searchInTables(tokens.get(posicio_inici+1), symbols);
                    //System.out.println(tokens.get(posicio_inici+1));
                    if (s.Type == 0){

                        typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "int"));
                    }

                }else{
                    if (s.Reserved_Word.equals("char")){

                        s = searchInTables(tokens.get(posicio_inici+1), symbols);
                        //System.out.println(tokens.get(posicio_inici+1));
                        if (s.Type == 0){

                            typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "char"));
                        }

                    }else{
                        if (s.Reserved_Word.equals("float")){
                            s = searchInTables(tokens.get(posicio_inici+1), symbols);
                            //System.out.println(tokens.get(posicio_inici+1));
                            if (s.Type == 0){

                                typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "float"));
                            }
                        }
                    }
                }
            }else{
                if (s.Type == 7){
                    /*if (s.Reserved_Word.equals("if")) {
                        posicio_inici++;
                        s = symbols.search(tokens.get(posicio_inici)); // '('
                        posicio_inici++;
                        s = symbols.search(tokens.get(posicio_inici));
                        String typeLeft;

                        if (symbols.search(s.Reserved_Word) != null && symbols.search(s.Reserved_Word).Type == 0){ //variable
                            typeLeft = typeTable.get(s.Reserved_Word).varType;
                        }else{                                                                                     //nombre

                        }

                    }else{ //s.Reserved_Word.equals("else")

                    }*/
                }
            }
        }
    }


    public boolean Errors(){
        return sm.mostrarErrors();
    }


    public Symbol searchInTables(String token, List<SymbolTable> taulaS){
        for (int i = taulaS.size() - 1; i >= 0; i--){
            Symbol sym = taulaS.get(i).search(token);
            if(sym != null){
                return sym;
            }
        }
        return null;
    }
}


