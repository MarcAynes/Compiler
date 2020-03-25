package ErrorHandler;

import java.util.LinkedList;

public class SyntacticalError extends ErrorHandler {

    public void addSyntacticError(String s){
        addError("S'ha trobat un error sintàctic : " + s +".");
    }
}
