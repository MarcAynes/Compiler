package Semantic;



import ErrorHandler.SemanticalError;
import TaulaDeSimbols.Symbol;
import TaulaDeSimbols.SymbolTable;

import java.util.HashMap;
import java.util.List;

public class Semantic {
    HashMap<String, Tipus> typeTable = new HashMap<String, Tipus>();
    SemanticalError sm = new SemanticalError();

    public void semanticAnalysis(SymbolTable symbols, List<String> tokens){

        Symbol s = symbols.search(tokens.get(0));
        if (s.Type == 0){       // var

            Tipus t = typeTable.get(s.Reserved_Word);
            if (t.varType.equals("int")){

                for(int i = 1; !tokens.get(i).equals(";"); i++){
                    s = symbols.search(tokens.get(i));
                    if (s.Type == 0 && (typeTable.get(s.Reserved_Word).varType.equals("int") || typeTable.get(s.Reserved_Word).varType.equals("char"))){


                    }else{
                        if (s.Type == 0){

                            //throw new TypeException("Integer can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            sm.addSemanticalError("Integer can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            break;
                        }
                    }
                }

            }else{
                if(t.varType.equals("char")){

                    for(int i = 1; !tokens.get(i).equals(";"); i++){

                        s = symbols.search(tokens.get(i));
                        if (s.Type == 0 && !typeTable.get(s.Reserved_Word).varType.equals("char")){

                            //throw new TypeException("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            sm.addSemanticalError("char can not be assigned to " + typeTable.get(s.Reserved_Word).varType);
                            break;
                        }
                    }

                }
            }
        }else{

            if (s.Type == 4){           //declaration
                if (s.Reserved_Word.equals("int")) {

                    s = symbols.search(tokens.get(1));

                    if (s.Type == 0){

                        typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "int"));
                    }

                }else{
                    if (s.Reserved_Word.equals("char")){

                        s = symbols.search(tokens.get(1));

                        if (s.Type == 0){

                            typeTable.put(s.Reserved_Word, new Tipus(s.Reserved_Word, "char"));
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
