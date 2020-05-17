package ErrorHandler;

public class SemanticalError extends ErrorHandler {

    public SemanticalError(){

    }

    public void addSemanticalError(String s){
        addError("S'ha trobat un error semàntic : " + s +".");
    }
}
