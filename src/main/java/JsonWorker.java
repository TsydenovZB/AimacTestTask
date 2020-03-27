import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class JsonWorker {
    public void jsonReader(String input, String operationType) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(input));
        Map<?, ?> criterias = gson.fromJson(reader, Map.class);
        if(operationType.equals("search")) {
            ArrayList<?> arrayList = (ArrayList<?>) criterias.get("criterias");
            for (Object o : arrayList) {
                LinkedTreeMap<?, ?> linkedTreeMap = (LinkedTreeMap<?, ?>) o;
                if(linkedTreeMap.containsKey("lastName")) {
                    String lastName = String.valueOf(linkedTreeMap.get("lastName"));
                    // вызов функцию запроса в базу данных
                } else if (linkedTreeMap.containsKey("productName") && linkedTreeMap.containsKey("minTimes")) {
                    String productName = (String) linkedTreeMap.get("productName");
                    Integer minTimes = (Integer) linkedTreeMap.get("minTimes");
                    // вызов функцию запроса в базу данных
                } else if (linkedTreeMap.containsKey("minExpenses") && linkedTreeMap.containsKey("maxExpenses")) {
                    Integer minExpenses = (Integer) linkedTreeMap.get("minExpenses");
                    Integer maxExpenses = (Integer) linkedTreeMap.get("maxExpenses");
                    // вызов функцию запроса в базу данных
                } else if (linkedTreeMap.containsKey("badCustomers")) {
                    Integer badCustomers = (Integer) linkedTreeMap.get("badCustomers");
                    // вызов функцию запроса в базу данных
                } else {
                    Main.isError = true;
                    Main.errorMessage = "Некорретные данные для поиска";
                }
            }
        } else if (operationType.equals("stat")) {
            // если тип операции stat
        } else {
            Main.isError = true;
            Main.errorMessage = "Неправильно указан тип операции";
        }
    }
}
