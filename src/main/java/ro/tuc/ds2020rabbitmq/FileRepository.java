package ro.tuc.ds2020rabbitmq;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

    public void fileWriter(String path, String string) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(string + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> fileReader(String path){
        BufferedReader br;
        List<String> lines = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}