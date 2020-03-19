package ErrorHandler;

public class LexicalError extends ErrorHandler{

    private String Error;


    public LexicalError(String s){

        String a;
        a= "S'ha trobat un error de lexic : " + s +".";


        addError(a);

    }


}
