package Adresscode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class GeneratorTAC {

    private File file;

    public void generator(Separacio separation) {
        LinkedList<Operacio> operations = separation.operacions;
        StringBuilder stringToWrite = new StringBuilder();

        file = new File ("src/GeneratedTAC/TAC");

        for (int i = 0; i < operations.size(); i++) {
            if(operations.get(i).getOp().equals("-"))
            {
                stringToWrite.append("t").append(i).append(" ").append("=").append(" ").append(operations.get(i).getOp()).append(" ").append(operations.get(i).getArg1()).append(";\n");
            }else if(operations.get(i).getOp().equals("=")){
                stringToWrite.append(operations.get(i).getArg1()).append(" ").append(operations.get(i).getOp()).append(" ").append(operations.get(i).getArg2()).append(";\n");
            }else{
                stringToWrite.append("t").append(i).append(" ").append("=").append(" ").append(operations.get(i).getArg1()).append(" ").append(operations.get(i).getOp()).append(" ").append(operations.get(i).getArg2()).append(";\n");
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
