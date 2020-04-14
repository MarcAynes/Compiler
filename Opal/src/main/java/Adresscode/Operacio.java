package Adresscode;

public class Operacio {

    private String op;
    private String arg1;
    private String arg2;
    private String result;
    private String tipus;
    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }



    public Operacio(String op, String arg1, String arg2,String result,String tipus) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
        this.tipus = tipus;
    }


    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getResult() {
        return result;
    }
}
