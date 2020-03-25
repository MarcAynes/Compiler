package ErrorHandler;

public class SemanticalError extends ErrorHandler {

    public void addSemanticalError(String s){
        addError("S'ha trobat un error semàntic : " + s +".");
    }
}
