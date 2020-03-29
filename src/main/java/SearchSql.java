import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchSql {
    private String url;
    private String login;
    private String password;
    private String className = "org.postgresql.Driver";

    public SearchSql() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("DbConnection.json"));
        Map<?, ?> params = gson.fromJson(reader, Map.class);
        if (params.containsKey("url") && params.containsKey("login") && params.containsKey("password")) {
            this.url = (String) params.get("url");
            this.login = (String) params.get("login");
            this.password = (String) params.get("password");
        } else {
            Main.isError = true;
            Main.errorMessage = "Указаны некорректные данные подключения к базе данных";
        }
    }


    public ArrayList<?> findBuyersWithLastName(String lastName) {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName(className);

            if (connection == null) {
                Main.isError = true;
                Main.errorMessage = "Нет подключения с базой данных";
                return null;
            } else {
                String sqlQuery = "SELECT firstname, lastname FROM buyers WHERE lastname = ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, lastName);
                return findBuyers(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            Main.isError = true;
            Main.errorMessage = e.getMessage();
            return null;
        }
    }

    public ArrayList<?> findBuyersProductNameByMinTimes(String productName, Double minTimes) {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName(className);

            if (connection == null) {
                Main.isError = true;
                Main.errorMessage = "Нет подключения с базой данных";
                return null;
            } else {
                String sqlQuery = "SELECT buyers.id, buyers.lastname, buyers.firstname " +
                        "FROM  buyers, products, purchases  WHERE products.id = purchases.products_id " +
                        "AND purchases.buyers_id = buyers.id " +
                        "AND products.productname = ? " +
                        "GROUP BY buyers.id, buyers.lastname, buyers.firstname " +
                        "HAVING COUNT (products.productname) > ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, productName);
                statement.setDouble(2, minTimes);

                return findBuyers(statement);
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

    public ArrayList<?> findBuyersWithExpenses(Double minExpenses, Double maxExpenses) {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName(className);

            if (connection == null) {
                Main.isError = true;
                Main.errorMessage = "PostgreSQL Driver не найден";
                return null;
            } else {
                String sqlQuery = "SELECT buyers.id, buyers.lastName, buyers.firstName " +
                        "FROM  buyers, products, purchases  WHERE purchases.buyers_id = buyers.id " +
                        "AND purchases.products_id = products.id " +
                        "GROUP BY buyers.id, buyers.lastName, buyers.firstName " +
                        "HAVING SUM(products.expenses) BETWEEN CAST(? AS double precision) AND CAST(? AS double precision);";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setDouble(1, minExpenses);
                statement.setDouble(2, maxExpenses);

                return findBuyers(statement);
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

    public ArrayList<?> findBadCustomers(Double badCustomers) {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName(className);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT buyers.id, buyers.lastName, buyers.firstName " +
                        "FROM  buyers, products, purchases  WHERE purchases.buyers_id = buyers.id " +
                        "AND purchases.products_id = products.id " +
                        "GROUP BY buyers.id, buyers.lastName, buyers.firstName " +
                        "ORDER BY COUNT(products.productname) LIMIT ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setDouble(1, badCustomers);

                return findBuyers(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            Main.isError = true;
            Main.errorMessage = e.getMessage();
            return null;
        }
    }

    public ArrayList<?> findBuyers(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {

            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            while (resultSet.next()) {
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("firstName", resultSet.getString("firstName"));
                hashMap.put("lastName", resultSet.getString("lastName"));

                arrayList.add(hashMap);
            }

            return arrayList;
        } catch (SQLException e) {
            Main.isError = true;
            Main.errorMessage = e.getMessage();
            return null;
        }
    }
}
