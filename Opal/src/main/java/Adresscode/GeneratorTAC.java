package Adresscode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class GeneratorTAC {

    public File generator(Separacio separation, int i) {

        LinkedList<Operacio> operations = separation.operacions;
        StringBuilder stringToWrite = new StringBuilder();
        File file = new File("src/GeneratedTAC/TAC"+i);
        int temporal = 1;

        for (Operacio operation : operations) {
            if (operation.getTipus().equals("if")) {
                if (operation.getOp().equals("!=") || operation.getOp().equals("==") || operation.getOp().equals("<=") || operation.getOp().equals("<") || operation.getOp().equals(">") || operation.getOp().equals(">=")) {
                    if (operation.getOp().equals("-")) {
                        stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append("0").append(" ").append(operation.getOp()).append(" ").append(operation.getArg1()).append(";\n");
                        temporal++;
                    } else if(operation.getOp().equals("*")){
                        stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                        temporal++;
                    } else if (operation.getOp().equals("=")) {
                        stringToWrite.append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                    } else {
                        stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                        stringToWrite.append("if(!t").append(temporal).append(") goto Fiif").append(operation.getResult()).append("\n");
                    }
                }
            } else if (operation.getTipus().equals("while")) {
                stringToWrite.append("loop").append(operation.getResult()).append("\n");
                if (operation.getOp().equals("!=") || operation.getOp().equals("==") || operation.getOp().equals("<=") || operation.getOp().equals("<") || operation.getOp().equals(">") || operation.getOp().equals(">=")) {
                    if (operation.getOp().equals("-")) {
                        stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append("0").append(" ").append(operation.getOp()).append(" ").append(operation.getArg1()).append(";\n");
                        temporal++;
                    }else if(operation.getOp().equals("*")){
                        stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                        temporal++;
                    }
                    else if (operation.getOp().equals("=")) {
                        stringToWrite.append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                    } else {
                        stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                        stringToWrite.append("if(!t").append(temporal).append(") goto Fiwhile").append(operation.getResult()).append("\n");
                    }
                }
            }else {
                if (operation.getOp().equals("-")) {
                    stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append("0").append(" ").append(operation.getOp()).append(" ").append(operation.getArg1()).append(";\n");
                    temporal++;
                } else if(operation.getOp().equals("*")){
                    stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                    temporal++;
                } else if (operation.getOp().equals("Fiif")) {
                    stringToWrite.append(operation.getOp()).append(operation.getResult()).append("\n");
                }
                else if (operation.getOp().equals("Fiwhile")) {
                    stringToWrite.append("goto loop").append(operation.getResult()).append("\n");
                    stringToWrite.append(operation.getOp()).append(operation.getResult()).append("\n");
                }else if (operation.getOp().equals("=")) {
                    stringToWrite.append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                } else {
                    stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operation.getArg1()).append(" ").append(operation.getOp()).append(" ").append(operation.getArg2()).append(";\n");
                    temporal++;
                }
            }
        }

        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (output != null) {
                output.write(stringToWrite.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
