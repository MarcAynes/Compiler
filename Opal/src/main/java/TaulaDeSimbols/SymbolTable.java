package TaulaDeSimbols;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class SymbolTable {

    HashMap<String, Symbol> Table = new HashMap<String, Symbol>();

    public SymbolTable(String file){

        Gson gson = new Gson();
        try {
            Symbol[] sym = gson.fromJson(new FileReader(file), Symbol[].class);
            for (int i = 0; i < sym.length; i++){
                add(sym[i]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Symbol search(String s){

        return this.Table.get(s);
    }

    public boolean add(Symbol s){
        if (this.Table.get(s.Reserved_Word) == null) {
            this.Table.put(s.Reserved_Word, s);
            return true;
        }else{
            return false;
        }
    }

    public boolean delete(Symbol s){

        return this.Table.remove(s.Reserved_Word, s);
    }

    public int getType(String s){

        return Table.get(s).Type;
    }

    public boolean update(Symbol s){

        if (Table.get(s.Reserved_Word) == null) {
            Table.remove(s.Reserved_Word);
            Table.put(s.Reserved_Word, s);
            return true;
        }else{
            return false;
        }
    }





}
