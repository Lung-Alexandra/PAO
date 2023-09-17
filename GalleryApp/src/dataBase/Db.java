package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Db database = null;
    private final Connection connection;

    private Db() {
        String DB_URL = System.getenv("DB_URL");
        String DB_USER = System.getenv("DB_USER");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Db getDatabase() {
        if (database == null)
            database = new Db();
        return database;
    }

    public Connection getConnection(){
        return connection;
    }
}
