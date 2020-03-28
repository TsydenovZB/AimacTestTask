import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

public class JsonWorker {
    public void jsonReader(String input, String operationType) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(input));

        Map<?, ?> criterias = gson.fromJson(reader, Map.class);
        LinkedTreeMap<String, String> results = new LinkedTreeMap<>();

        if(operationType.equals("search")) {
            results.put("type", "search");
            ArrayList<?> arrayList = (ArrayList<?>) criterias.get("criterias");
            for (Object o : arrayList) {
                LinkedTreeMap<?, ?> linkedTreeMap = (LinkedTreeMap<?, ?>) o;
                if(linkedTreeMap.containsKey("lastName")) {
                    String lastName = String.valueOf(linkedTreeMap.get("lastName"));
                    // вызов функцию запроса в базу данных
                    SearchSql searchSql = new SearchSql();
                    ArrayList<?> query = searchSql.findBuyersWithLastName(lastName);
                    System.out.println("ss");
                } else if (linkedTreeMap.containsKey("productName") && linkedTreeMap.containsKey("minTimes")) {
                    String productName = (String) linkedTreeMap.get("productName");
                    Double minTimes = (Double) linkedTreeMap.get("minTimes");
                    // вызов функцию запроса в базу данных
                } else if (linkedTreeMap.containsKey("minExpenses") && linkedTreeMap.containsKey("maxExpenses")) {
                    Double minExpenses = (Double) linkedTreeMap.get("minExpenses");
                    Double maxExpenses = (Double) linkedTreeMap.get("maxExpenses");
                    // вызов функцию запроса в базу данных
                } else if (linkedTreeMap.containsKey("badCustomers")) {
                    Double badCustomers = (Double) linkedTreeMap.get("badCustomers");
                    // вызов функцию запроса в базу данных
                } else {
                    Main.isError = true;
                    Main.errorMessage = "Некорретные данные для операции search";
                }
            }

            String jsonObject = gson.toJson(results, results.getClass());
            System.out.println("aaaaaaaa");
        } else if (operationType.equals("stat")) {
            results.put("type", "stat");
            if (criterias.containsKey("startDate") && criterias.containsKey("endDate")) {
                System.out.println(criterias.get("startDate").getClass());
                Date startDate = Date.valueOf((String) criterias.get("startDate"));
                Date endDate = (Date) criterias.get("endDate");
                
            }
        } else {
            Main.isError = true;
            Main.errorMessage = "Неправильно указан тип операции";
        }
    }
}
