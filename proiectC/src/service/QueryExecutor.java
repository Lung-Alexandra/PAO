package service;

import dataBase.Db;

import java.sql.*;

public class QueryExecutor {
    private static final Db database = Db.getDatabase();
    private static final Connection con = database.getConnection();

    public static ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery(sql);
    }

    public static PreparedStatement executeUpdate(String sql) throws SQLException {
        return con.prepareStatement(sql);
    }
}