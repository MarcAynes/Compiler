package ErrorHandler;

public class LexicalError extends ErrorHandler{

    public void addLexicalError(String s){
        addError("S'ha trobat un error de lèxic : " + s +".");
    }
}
