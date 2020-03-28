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
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT firstname, lastname FROM buyers WHERE lastname = ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, lastName);
                return executeRequest(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }

    public ArrayList<?> executeRequest(PreparedStatement statement) throws SQLException {
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
            System.out.println(e.getMessage());
            return null;
        }
    }
}
