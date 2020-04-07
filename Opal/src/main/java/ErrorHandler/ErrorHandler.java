package ErrorHandler;

import java.util.LinkedList;

public class ErrorHandler {
    private LinkedList<String> error;

    public ErrorHandler(){
        this.error  = new LinkedList<>();
    }

    public LinkedList<String> getError() {
        return error;
    }

    public void setError(LinkedList<String> error) {
        this.error = error;
    }

    public boolean mostrarErrors(){
         if(this.error.size() > 0) {
             for (String anError : this.error) {
                 System.out.println(anError);
             }
         }
         else{
             return true;
         }
         return false;
    }

    public void addError(String s){
        this.error.add(s);
    }
}
