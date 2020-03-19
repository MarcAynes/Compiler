package ErrorHandler;

import java.util.LinkedList;

public class ErrorHandler {

    public LinkedList<String> getError() {
        return error;
    }

    public void setError(LinkedList<String> error) {
        this.error = error;
    }

    private LinkedList<String> error;


    public void mostrarErrors(){


    try {


    for(int i=0;i<error.size();i++ ){

        System.out.println( error.get(i));



    }
    }catch (NullPointerException e){


    }




    }

    public void addError(String s){

        error.add(s);




    }



}
