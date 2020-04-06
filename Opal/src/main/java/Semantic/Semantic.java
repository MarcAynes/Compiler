package Semantic;

import ErrorHandler.SemanticalError;
import TaulaDeSimbols.Symbol;
import TaulaDeSimbols.SymbolTable;

import java.util.HashMap;
import java.util.List;

public class Semantic {
    HashMap<String, Tipus> typeTable = new HashMap<String, Tipus>();
    SemanticalError sm = new SemanticalError();

    public void semanticAnalysis(SymbolTable symbols, List<String> tokens, int posicio_inici){

        Symbol s = symbols.search(tokens.get(posicio_inici));
        if (s.Type == 0){       // var

            Tipus t = typeTable.get(s.Reserved_Word);
            if (t.varType.equals("int")){

                for(int i = posicio_inici+1; !tokens.get(i).equals(";"); i++){
                    s = symbols.search(tokens.get(i));
                    System.out.println(tokens.get(i));
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

                        s = symbols.search(tokens.get(i));
                        System.out.println(tokens.get(i));
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

                            s = symbols.search(tokens.get(i));
                            System.out.println(tokens.get(i));
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

                    s = symbols.search(tokens.get(posicio_inici+1));
                    System.out.println(tokens.get(posicio_inici+1));
                    if (s.Type == 0){

                        typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "int"));
                    }

                }else{
                    if (s.Reserved_Word.equals("char")){

                        s = symbols.search(tokens.get(posicio_inici+1));
                        System.out.println(tokens.get(posicio_inici+1));
                        if (s.Type == 0){

                            typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "char"));
                        }

                    }else{
                        if (s.Reserved_Word.equals("float")){
                            s = symbols.search(tokens.get(posicio_inici+1));
                            System.out.println(tokens.get(posicio_inici+1));
                            if (s.Type == 0){

                                typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "float"));
                            }
                        }
                    }
                }
            }
        }
    }


    public void Errors(){
        sm.mostrarErrors();
    }
}
