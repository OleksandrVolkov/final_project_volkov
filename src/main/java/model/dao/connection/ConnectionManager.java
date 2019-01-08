package model.dao.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {
    static private Connection connection;
    static private String url = "jdbc:mysql://127.0.0.1:3306/mydbtest";
    static Statement statement;


    public static Connection getConnection(){
        try {
            if(connection == null) {
                Driver driver = new com.mysql.jdbc.Driver();
                Properties properties = new Properties();
                properties.put("user", "root");
                properties.put("password", "");
                connection = driver.connect(url, properties);
                statement = connection.createStatement();
            }else {
                return connection;
            }
        } catch (SQLException e) {
            System.out.println("Не найден драйвер");
            e.printStackTrace();
        }
        return connection;
    }
}
