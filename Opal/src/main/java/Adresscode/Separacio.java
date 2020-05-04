package Adresscode;

import java.util.LinkedList;
import java.util.List;

import Scanner.Scanner;

public class Separacio {

    private Scanner scanner;
    public LinkedList<Operacio> operacions;

    public Separacio(Scanner s) {

        this.scanner = s;
        operacions=new LinkedList<>();

    }

    public void separar() {

        List<String> tokens = scanner.getTokens();

        int lastToken = 1;
        int primer = 1;
        int primerM = 1;
        int resta=0;
        int j=0;
        int aqui=0;
        Operacio auxiliar = new Operacio();
        String tipus = new String();
        String arg1 = new String();
        String arg2= new String();
        String aux=new String();
        String var=new String();
        LinkedList<Integer> whilee =new LinkedList<>();
        String tipusFuncio=new String();
        int num=1;
        int ifnivell=0;
        LinkedList<Integer> ifnivell2=new LinkedList<>();

        int parent=0;
        Operacio o ;
        String auxComp=new String();
        whilee.addFirst(-1);

        if (tokens != null) {

            for (int i=0;i<tokens.size();i++) {

                if (tokens.get(i).equals(";") || tokens.get(i).equals("{")) {

                    lastToken = 1;

                    if(aqui==1){
                        aqui=0;
                        num--;
                        o=new Operacio("=",var,"t"+num, " ","");
                        operacions.add(o);
                        num++;

                    }


                } else {

                    if (lastToken == 1) {
                        lastToken=0;
                        primer=1;
                        primerM=1;

                        if (tokens.get(i).equals("float") || tokens.get(i).equals("int") || tokens.get(i).equals("char") || tokens.get(i).equals("if") || tokens.get(i).equals("}")|| tokens.get(i).equals("while")) {



                            if(tokens.get(i).equals("if")){

                                ifnivell++;


                                ifnivell2.addFirst(ifnivell);
                                tipusFuncio="if";

                                //o=new Operacio("if","","", ""+ ifnivell,"");
                                //operacions.add(o);

                            }
                            if(tokens.get(i).equals("while")){

                                ifnivell++;
                                whilee.addFirst(ifnivell);
                                ifnivell2.addFirst(ifnivell);
                                tipusFuncio="while";

                                //o=new Operacio("if","","", ""+ ifnivell,"");
                                //operacions.add(o);

                            }
                            if(tokens.get(i).equals("}")){


                                if(whilee.get(0)==ifnivell2.get(0)){


                                    o=new Operacio("Fiwhile","","", ""+ ifnivell2.get(0),"");
                                    operacions.add(o);
                                    ifnivell2.removeFirst();
                                    whilee.removeFirst();
                                    i--;
                                    lastToken = 1;


                                }else{
                                    o=new Operacio("Fiif","","", ""+ ifnivell2.get(0),"");
                                    operacions.add(o);
                                    ifnivell2.removeFirst();
                                    i--;
                                    lastToken = 1;
                                }


                            }

                            //tipus="var";
                            //arg1=tokens.get(i);
                            //arg2=tokens.get(i+1);
                            //o=new Operacio(tipus,arg1,arg2);
                            //operacions.add(o);
                            i++;


                            //num++;

                        }else{

                            tipus="=";
                            arg1=tokens.get(i);
                            arg2="t"+num;

                            if(tokens.get(i+3).equals(";")){
                                arg2=tokens.get(i+2);
                                o=new Operacio(tipus,arg1,arg2," ","");
                                operacions.add(o);
                                i++;
                                i++;

                            }else{
                                aqui=1;
                                var=arg1;
                                i++;


                            }


                        }

                    }else{

                        arg1=tokens.get(i);

                        switch (tokens.get(i+1)){

                            case "+":
                                primerM=1;

                                if(primer==1){
                                    primer=0;

                                    tipus = tokens.get(i + 1);
                                    arg2 = "t" + num;
                                    o = new Operacio(tipus, arg1, tokens.get(i + 2),"t"+num,"");

                                    operacions.add(o);
                                    num++;
                                }else{
                                    tipus = tokens.get(i + 1);

                                    arg2 =tokens.get(i + 2);
                                    num--;
                                    arg1 = "t" + num;
                                    num++;
                                    o = new Operacio(tipus, arg1, arg2,"t"+num,"");
                                    num++;

                                    operacions.add(o);


                                }

                                break;
                            case "-":
                                primerM=1;
                                tipus = tokens.get(i + 1);
                                arg2 = tokens.get(i + 2);

                                o = new Operacio(tipus, arg2, " ","t"+num,"");

                                operacions.add(o);
                                num++;

                                if(primer==0){
                                    num--;
                                    arg1 = "t" +num;
                                    num++;
                                    arg2 = "t" +num;
                                    num++;
                                    o = new Operacio("+", arg1, arg2,"t"+num,"");
                                    operacions.add(o);
                                    num++;

                                }else{

                                    j=num-1;
                                    o = new Operacio("+", arg1, "t" + j,"t"+num,"");
                                    operacions.add(o);
                                    num++;

                                }

                                resta = 1;


                                break;
                            case "*":


                                if(primer==1){
                                    primerM=2;
                                    primer=0;

                                    tipus = tokens.get(i + 1);
                                    arg2 = "t" + num;
                                    o = new Operacio(tipus, tokens.get(i ), tokens.get(i + 2),"t"+num,"");

                                    operacions.add(o);
                                    num++;
                                }else{
                                    if(primerM==1){
                                        primerM=3;
                                        auxiliar=operacions.getLast();
                                        operacions.removeLast();
                                        tipus = tokens.get(i + 1);

                                        arg2 =tokens.get(i + 2);
                                        num--;
                                        arg1 = tokens.get(i);

                                        o = new Operacio(tipus, arg1, arg2,"t"+num,"");
                                        num++;

                                        operacions.add(o);


                                    }else{


                                        if(primerM==3) {

                                            tipus = tokens.get(i + 1);

                                            arg2 = tokens.get(i + 2);
                                            num--;
                                            arg1 = "t" + num;
                                            num++;
                                            o = new Operacio(tipus, arg1, arg2, "t" + num, "");
                                            num++;

                                            operacions.add(o);
                                        }


                                        }

                                    if(primerM==3 && !(tokens.get(i+3).equals("*"))) {

                                        primerM=0;
                                        int num2=num-1;
                                        o=new Operacio("+",auxiliar.getArg1(),"t"+num2,"t"+num,"");
                                       operacions.add(o);
                                        num++;


                                    }



                                }


                                break;
                            case ")":

                                num--;
                                o = new Operacio(auxComp, aux, "t" + num,""+ifnivell,tipusFuncio);
                                operacions.add(o);
                                i++;
                                num++;
                                break;


                            case "<":case "==":case "<=":case ">=":

                                primerM=1;
                                auxComp=tokens.get(i+1);
                                num--;
                                aux="t"+num;
                                num++;
                                primer=1;
                                if(tokens.get(i-1).equals("(")){

                                    aux=tokens.get(i);

                                }

                                if(tokens.get(i+3).equals(")")){

                                    num--;
                                    o = new Operacio(auxComp, aux, tokens.get(i+2),""+ifnivell,tipusFuncio);
                                    operacions.add(o);
                                    i++;
                                    num++;
                                    i=i+2;

                                }

                                break;



                        }







                    }


                }

            }


        }
    }


}
