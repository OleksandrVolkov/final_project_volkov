package model.dao.connection;



//import org.apache.log4j.Logger;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class ConnectionManager {
    private static Connection connection;
    private static String url = "jdbc:mysql://127.0.0.1:3306/mydbtest";
    static Statement statement;
    private static Logger log = Logger.getLogger(ConnectionManager.class);


    public static Connection getConnection(){
        try {
            log.trace("getting connection...");
            if(connection == null) {
                log.trace("instantiating db properties");
                Driver driver = new com.mysql.jdbc.Driver();
                Properties properties = new Properties();
                properties.put("user", "root");
                properties.put("password", "");
                log.trace("instantiating connection");
                connection = driver.connect(url, properties);
                statement = connection.createStatement();
            }else {
                log.warn("connection already exists");
                return connection;
            }
        } catch (SQLException e) {
            log.error("driver is not found: " + e);
            System.out.println("Не найден драйвер");
            e.printStackTrace();
        }
        log.trace("returning connection");
        return connection;
    }
}
