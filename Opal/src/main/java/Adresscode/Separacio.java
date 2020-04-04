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
        int resta=0;
        int j=0;
        int aqui=0;
        String tipus = new String();
        String arg1 = new String();
        String arg2= new String();
        String aux=new String();
        String var=new String();
        int num=1;
        Operacio o ;

        if (tokens != null) {

            for (int i=0;i<tokens.size();i++) {

                if (tokens.get(i).equals(";")) {

                    lastToken = 1;

                    if(aqui==1){
                        aqui=0;
                        num--;
                        o=new Operacio("=",var,"t"+num, " ");
                        operacions.add(o);
                        num++;

                    }


                } else {

                    if (lastToken == 1) {
                        lastToken=0;
                        primer=1;

                        if (tokens.get(i).equals("float") || tokens.get(i).equals("int") || tokens.get(i).equals("char")) {

                            tipus="var";
                            arg1=tokens.get(i);
                            arg2=tokens.get(i+1);
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
                                o=new Operacio(tipus,arg1,arg2," ");
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

                            if(tokens.get(i+1).equals("+")) {

                                if(primer==1){
                                    primer=0;

                                    tipus = tokens.get(i + 1);
                                    arg2 = "t" + num;
                                    o = new Operacio(tipus, arg1, tokens.get(i + 2),"t"+num);

                                    operacions.add(o);
                                    num++;
                                }else{
                                    tipus = tokens.get(i + 1);

                                    arg2 =tokens.get(i + 2);
                                    num--;
                                    arg1 = "t" + num;
                                    num++;
                                    o = new Operacio(tipus, arg1, arg2,"t"+num);
                                    num++;

                                    operacions.add(o);


                                }



                            }else{

                                if(tokens.get(i+1).equals("-")) {

                                    tipus = tokens.get(i + 1);
                                    arg2 = tokens.get(i + 2);

                                    o = new Operacio(tipus, arg2, " ","t"+num);

                                    operacions.add(o);

                                    if(primer==0){
                                        num--;
                                        arg1 = "t" +num;
                                        num++;
                                        arg2 = "t" +num;
                                        num++;
                                        o = new Operacio("+", arg1, arg2,"t"+num);
                                        operacions.add(o);
                                        num++;

                                    }else{


                                        o = new Operacio("+", arg1, "t" + num,"t"+num);
                                        operacions.add(o);
                                        num++;

                                    }










                                    resta = 1;
                                }


                        }



                    }


                }

            }


        }
    }


}
