package Scanner;

import TaulaDeSimbols.Symbol;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*
*   On creation: creates a trie tree which is filled with the reserved words in the file Symbols.json
*
*   getToken: return a token (string) if could find a token in the code file, if is end of file return null
*
*
 */

public class Scanner {

    private Trie trie = new Trie();
    List<String> tokens = new ArrayList<>();
    private BufferedReader br;

    public Scanner(String file){

        try {
            FileReader f = new FileReader(new File(file));
            br = new BufferedReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        try {
            Symbol[] sym = gson.fromJson(new FileReader("src/Symbols.json"), Symbol[].class);
            for (int i = 0; i < sym.length; i++){
                char[] c = sym[i].Reserved_Word.toCharArray();
                trie.add(c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getToken(){

        if (tokens.size() > 0){
            return tokens.remove(0);
        }

        try {

            String aux = br.readLine();
            while(aux != null && aux.length() == 0){
                aux = br.readLine();
            }
            if (aux != null) {
                char[] frase = aux.toCharArray();

                String token = "";
                for (int i = 0; i < frase.length; i++) {

                    char[] a = new char[1];
                    a[0] = frase[i];
                    Return r = trie.search(a);

                    if (r.nombre == 1) {
                        if (token != "") {
                            tokens.add(token);
                            token = "";
                        }
                        int index = trie.compara(frase, i);
                        if (index > -1){
                            while(i <= index){
                                token += frase[i];
                                i++;
                            }
                            i--;
                        }else{
                            token += frase[i];
                        }

                        tokens.add(token);
                        token = "";

                    } else {

                        if (frase[i] == ' ' || frase[i] == '\t' || frase[i] == '\n') {
                            if (token.length() > 0 && i > 0 && (frase[i-1] != ' ' && frase[i-1] != '\t' && frase[i-1] != '\n')) {
                                tokens.add(token);
                                token = "";
                            }
                        } else {
                            token += frase[i];

                            if (i+1 == frase.length){
                                tokens.add(token);
                            }
                        }

                    }

                }
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return tokens.remove(0);
    }

    public List<String> getTokens() {
        List<String> tokens = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            String token = getToken();
            if (token != null) {
                tokens.add(token);
            }else{
                break;
            }
        }
        return tokens;
    }
}
