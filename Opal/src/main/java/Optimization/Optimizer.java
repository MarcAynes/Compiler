package Optimization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Optimizer {

    public File optimization(File file)
    {
        File newFile = new File("src/OptimizedCode/OptimizedTAC");
        StringBuilder text = new StringBuilder();

        try(BufferedReader input = Files.newBufferedReader(Paths.get(String.valueOf(file)))){
            String line;
            while ((line = input.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter output = null;

        try {
            output = new BufferedWriter(new FileWriter(newFile));
            output.write(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                output.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return newFile;
    }
}
