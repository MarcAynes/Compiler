package Adresscode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class GeneratorTAC {

    public void generator(Separacio separation) {
        LinkedList<Operacio> operations = separation.operacions;
        StringBuilder stringToWrite = new StringBuilder();

        File file = new File("src/GeneratedTAC/TAC");
        int temporal = 1;

        for (int i = 0; i < operations.size(); i++) {
            if(operations.get(i).getOp().equals("-"))
            {
                stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operations.get(i).getOp()).append(" ").append(operations.get(i).getArg1()).append(";\n");
                temporal++;
            }else if(operations.get(i).getOp().equals("=")){
                stringToWrite.append(operations.get(i).getArg1()).append(" ").append(operations.get(i).getOp()).append(" ").append(operations.get(i).getArg2()).append(";\n");
            }else{
                stringToWrite.append("t").append(temporal).append(" ").append("=").append(" ").append(operations.get(i).getArg1()).append(" ").append(operations.get(i).getOp()).append(" ").append(operations.get(i).getArg2()).append(";\n");
                temporal++;
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
    }
}
