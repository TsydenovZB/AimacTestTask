import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

public class JsonWorker {
    public String jsonReader(String input, String operationType) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(input));

        Map<?, ?> criterias = gson.fromJson(reader, Map.class);
        LinkedTreeMap<String, Object> output = new LinkedTreeMap<>();
        LinkedTreeMap<String, Object> results = new LinkedTreeMap<>();

        ArrayList<Object> array = new ArrayList<>();

        if(operationType.equals("search")) {
            SearchSql searchSql = new SearchSql();

            output.put("type", operationType);
            ArrayList<?> arrayList = (ArrayList<?>) criterias.get("criterias");
            for (Object o : arrayList) {
                LinkedTreeMap<?, ?> linkedTreeMap = (LinkedTreeMap<?, ?>) o;
                if(linkedTreeMap.containsKey("lastName")) {
                    String lastName = String.valueOf(linkedTreeMap.get("lastName"));

                    results.put("criteria", o);

                    ArrayList<?> query = searchSql.findBuyersWithLastName(lastName);

                    results.put("results", query);
                    array.add(results);

                } else if (linkedTreeMap.containsKey("productName") && linkedTreeMap.containsKey("minTimes")) {
                    String productName = (String) linkedTreeMap.get("productName");
                    Double minTimes = (Double) linkedTreeMap.get("minTimes");

                    results.put("criteria", o);

                    ArrayList<?> query = searchSql.findBuyersProductNameByMinTimes(productName, minTimes);

                    results.put("results", query);
                    array.add(results);

                } else if (linkedTreeMap.containsKey("minExpenses") && linkedTreeMap.containsKey("maxExpenses")) {
                    Double minExpenses = (Double) linkedTreeMap.get("minExpenses");
                    Double maxExpenses = (Double) linkedTreeMap.get("maxExpenses");

                    results.put("criteria", o);

                    ArrayList<?> query = searchSql.findBuyersWithExpenses(minExpenses, maxExpenses);

                    results.put("results", query);
                    array.add(results);

                } else if (linkedTreeMap.containsKey("badCustomers")) {
                    Double badCustomers = (Double) linkedTreeMap.get("badCustomers");

                    results.put("criteria", o);

                    ArrayList<?> query = searchSql.findBadCustomers(badCustomers);

                    results.put("results", query);
                    array.add(results);

                } else {
                    Main.isError = true;
                    Main.errorMessage = "Некорретные данные для операции search";
                }
            }

            output.put("results", array);

        } else if (operationType.equals("stat")) {
            output.put("type", operationType);
            if (criterias.containsKey("startDate") && criterias.containsKey("endDate")) {
                System.out.println(criterias.get("startDate").getClass());
                Date startDate = Date.valueOf((String) criterias.get("startDate"));
                Date endDate = Date.valueOf((String) criterias.get("endDate"));

                long totalDaysCount = (endDate.getTime()-startDate.getTime())/1000/3600/24;
                if (totalDaysCount < 0) {
                    return null;
                }
                output.put("totalDays", totalDaysCount);
                StatSql statSql = new StatSql();
                ArrayList<?> arrayList = statSql.getInfo(startDate, endDate);

                output.put("customers", arrayList);
                output.put("totalExpenses", statSql.getTotalExpenses());
                output.put("avgExpenses", statSql.getAvgExpenses());

            }
        } else {
            Main.isError = true;
            Main.errorMessage = "Неправильно указан тип операции";
        }
        if (Main.isError){
            output.clear();
            output.put("type", "error");
            output.put("message", Main.errorMessage);
        }
        return gson.toJson(output);
    }
}
