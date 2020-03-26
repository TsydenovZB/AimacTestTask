import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonWorker {
    public void jsonReader(String input) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(input));
        Map<?,?> criterias = gson.fromJson(reader, Map.class);
        /*
        if(criterias.containsKey("criterias")) {
            System.out.println("aa");
        }
        System.out.println("");
        */
         */
    }
}
