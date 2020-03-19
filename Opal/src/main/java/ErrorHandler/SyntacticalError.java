package ErrorHandler;

public class SyntacticalError extends ErrorHandler {



    public  SyntacticalError(String s){

        String a;
        a= "S'ha trobat un error sintactic : " + s +".";

        addError(a);



    }



}
