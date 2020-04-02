package Adresscode;

import java.util.LinkedList;
import java.util.List;

import Scanner.Scanner;

public class Separacio {

    private Scanner scanner;
    private LinkedList<Operacio> operacions;
    public Separacio(Scanner s) {

        this.scanner = s;
        operacions=new LinkedList<>();

    }

    public void separar() {

        List<String> tokens = scanner.getTokens();

        int lastToken = 1;
        int resta=0;
        String tipus = new String();
        String arg1 = new String();
        String arg2= new String();
        String aux=new String();
        int num=1;
        Operacio o ;

        if (tokens != null) {

            for (int i=0;i<tokens.size();i++) {

                if (tokens.get(i).equals(";")) {

                    lastToken = 1;


                } else {

                    if (lastToken == 1) {
                        lastToken=0;

                        if (tokens.get(i).equals("float") || tokens.get(i).equals("int") || tokens.get(i).equals("char")) {

                            tipus="var";
                            arg1=tokens.get(i);
                            arg2=tokens.get(i+1);
                            o=new Operacio(tipus,arg1,arg2);
                            operacions.add(o);
                            i++;
                            num++;


                        }else{

                            tipus="=";
                            arg1=tokens.get(i);
                            arg2="t"+num;
                            num++;
                            o=new Operacio(tipus,arg1,arg2);
                            operacions.add(o);
                            i++;

                        }

                    }else{

                        if(resta==1){
                            num--;
                            num--;
                            arg1="t"+num;
                            num++;
                            num++;
                            resta=0;
                        }else{
                            arg1=tokens.get(i);

                        }



                        if(tokens.get(i+1).equals(";")){

                           tipus=" ";
                           arg2=" ";


                           o=new Operacio(tipus,arg1,arg2);
                           operacions.add(o);
                           num++;

                        }else{

                            if(tokens.get(i+1).equals("+")) {
                                tipus = tokens.get(i + 1);
                                arg2 = "t" + num;
                                o = new Operacio(tipus, arg1, arg2);
                                num++;
                                operacions.add(o);
                                i++;

                            }else{
                                num++;
                                o = new Operacio("+", arg1, "t"+num);
                                operacions.add(o);
                                tipus = tokens.get(i + 1);
                                arg2 = tokens.get(i + 2);
                                o = new Operacio(tipus, arg2, " ");

                                operacions.add(o);


                                num++;
                                i++;


                                resta=1;


                            }
                        }



                    }


                }

            }


        }
    }


}
