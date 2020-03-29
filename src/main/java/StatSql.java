import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatSql {
    private String url;
    private String login;
    private String password;
    private double totalExpenses;
    private double avgExpenses;
    private int customersCount;
    private String className = "org.postgresql.Driver";

    public StatSql() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("DbConnection.json"));
        Map<?, ?> params = gson.fromJson(reader, Map.class);
        if (params.containsKey("url") && params.containsKey("login") && params.containsKey("password")) {
            this.url = (String) params.get("url");
            this.login = (String) params.get("login");
            this.password = (String) params.get("password");
            this.totalExpenses = 0;
            this.avgExpenses = 0;
            this.customersCount = 0;
        } else {
            Main.isError = true;
            Main.errorMessage = "Указаны некорректные данные подключения к базе данных";
        }
    }


    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getAvgExpenses() {
        return avgExpenses;
    }



    public ArrayList<?> getInfo(Date startDate, Date endDate) {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName(className);

            if (connection == null) {
                Main.isError = true;
                Main.errorMessage = "Нет подключения с базой данных";
                return null;
            } else {
                String sqlQuery = "SELECT buyers.id, CONCAT (buyers.lastName, ' ', buyers.firstName) AS buyers_name, " +
                        "CAST(SUM(products.expenses) AS numeric) AS totalSum FROM  buyers, products, purchases  " +
                        "WHERE purchases.buyers_id = buyers.id " +
                        "AND purchases.products_id = products.id " +
                        "AND purchases.purchases_date BETWEEN CAST (? AS date) AND CAST (? AS date) " +
                        "GROUP BY buyers.id, buyers.lastName, buyers.firstName " +
                        "ORDER BY SUM(products.expenses) DESC;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, startDate.toString());
                statement.setString(2, endDate.toString());

                ResultSet resultSet = statement.executeQuery();

                ArrayList<HashMap<String, Object>> result = new ArrayList<>();

                while (resultSet.next()) {
                    HashMap<String, Object> map = new HashMap<>();
                    int customerId = resultSet.getInt("id");

                    map.put("name", resultSet.getString("buyers_name"));

                    ArrayList<?> jsonArray = getPurchasesInfo(customerId, startDate, endDate);
                    if (jsonArray == null) {
                        return null;
                    }
                    map.put("purchases", jsonArray);

                    double totalSum = resultSet.getDouble("totalSum");
                    map.put("totalExpenses", totalSum);

                    customersCount++;
                    totalExpenses += totalSum;

                    result.add(map);
                }

                if (customersCount == 0) {
                    avgExpenses = 0;
                } else {
                    avgExpenses = totalExpenses / customersCount;
                }

                return result;
            }
        } catch (ClassNotFoundException e) {
            Main.isError = true;
            Main.errorMessage = "PostgreSQL Driver не найден";
            return null;
        } catch (SQLException e) {
            Main.isError = true;
            Main.errorMessage = e.getMessage();
            return null;
        }
    }

    private ArrayList<?> getPurchasesInfo(int buyers_id, Date startDate, Date endDate) {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName(className);

            if (connection == null) {
                Main.isError = true;
                Main.errorMessage = "Нет подключения с базой данных";
                return null;
            } else {
                String sqlQuery = "SELECT products.productname, CAST(SUM(products.expenses) AS numeric) AS expenses " +
                        "FROM  buyers, products, purchases  " +
                        "WHERE buyers.id = ? " +
                        "AND purchases.buyers_id = buyers.id " +
                        "AND purchases.products_id = products.id " +
                        "AND purchases.purchases_date BETWEEN CAST (? AS date) AND CAST (? AS date) " +
                        "GROUP BY products.productname " +
                        "ORDER BY SUM(products.expenses) DESC;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, buyers_id);
                statement.setString(2, startDate.toString());
                statement.setString(3, endDate.toString());

                ResultSet resultSet = statement.executeQuery();

                ArrayList<HashMap<String, String>> result = new ArrayList<>();

                while (resultSet.next()) {
                    HashMap<String, String> map = new HashMap<>();

                    map.put("name", resultSet.getString("productname"));
                    map.put("expenses", resultSet.getString("expenses"));

                    result.add(map);
                }

                return result;
            }
        } catch (ClassNotFoundException e) {
            Main.isError = true;
            Main.errorMessage = "PostgreSQL Driver не найден";
            return null;
        } catch (SQLException e) {
            Main.isError = true;
            Main.errorMessage = e.getMessage();
            return null;
        }
    }
}
