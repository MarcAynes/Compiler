package ErrorHandler;

public class SemanticalError extends ErrorHandler {


    public SemanticalError(String s){

        String a;
        a= "S'ha trobat un error semantic : " + s +".";

        addError(a);


    }


}
