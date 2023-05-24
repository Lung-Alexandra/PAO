package service;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JdbcClass {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/galeriedefotografi";
 
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JdbcClass() {
    }

    public static int getLastElementId() throws SQLException {
        int lastElementId = 0;

        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Obținerea ultimului id din tabela Element
            String sql = "SELECT MAX(id) FROM Element";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Verificarea existenței unui rezultat
            if (resultSet.next()) {
                lastElementId = resultSet.getInt(1);
            }

            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastElementId;
    }

    public static void insertElement(Element element) throws SQLException {
        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


            // Exemplu de inserare a unui element în baza de date
            String sql = "INSERT INTO Element (id, name, description,size, creationDate) VALUES (?, ?, ?, ?, ?)";


            // Obținerea ultimului ID
            int lastId = getLastElementId() + 1;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(lastId));
            statement.setString(2, element.getName());
            statement.setString(3, element.getDescription());
            statement.setObject(4, element.getSize());
            statement.setObject(5, element.getCreationDate());
            statement.executeUpdate();
            statement.close();


            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertImagine(Imagine imagine) throws SQLException {
        try {
            // Configuring the JDBC connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            Element el = new Element(imagine.getName(), imagine.getDescription(), imagine.getSize(), imagine.getCreationDate());
            insertElement(el);

            int lastId = getLastElementId();
            // Inserting the Imagine with the last inserted id
            String imagineInsertSql = "INSERT INTO Imagine (id, resolution, location) VALUES (?, ?, ?)";
            PreparedStatement imagineInsertStatement = connection.prepareStatement(imagineInsertSql);
            imagineInsertStatement.setInt(1, lastId);
            imagineInsertStatement.setString(2, imagine.getResolution());
            imagineInsertStatement.setString(3, imagine.getLocation());
            imagineInsertStatement.executeUpdate();
            imagineInsertStatement.close();

            // Closing the JDBC connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertFotografie(Fotografie fotografie) throws SQLException {
        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            Imagine el = new Imagine(fotografie.getName(), fotografie.getDescription(), fotografie.getSize(), fotografie.getCreationDate(), fotografie.getResolution(), fotografie.getLocation());
            insertImagine(el);

            int lastId = getLastElementId();
            // Inserting the Fotografie with the last inserted id
            String fotografieInsertSql = "INSERT INTO Fotografie (id, cameraType, cameraSettings) VALUES (?, ?, ?)";
            PreparedStatement fotografieInsertStatement = connection.prepareStatement(fotografieInsertSql);
            fotografieInsertStatement.setInt(1, lastId);
            fotografieInsertStatement.setString(2, fotografie.getCameraType());
            fotografieInsertStatement.setString(3, fotografie.getCameraSettings());
            fotografieInsertStatement.executeUpdate();
            fotografieInsertStatement.close();

            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertVideoclip(Videoclip videoclip) throws SQLException {
        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            Element el = new Element(videoclip.getName(), videoclip.getDescription(), videoclip.getSize(), videoclip.getCreationDate());
            insertElement(el);

            int lastId = getLastElementId();
            // Inserting the Videoclip with the last inserted id
            String videoclipInsertSql = "INSERT INTO Videoclip (id, duration) VALUES (?, ?)";
            PreparedStatement videoclipInsertStatement = connection.prepareStatement(videoclipInsertSql);
            videoclipInsertStatement.setInt(1, lastId);
            videoclipInsertStatement.setInt(2, videoclip.getDuration());
            videoclipInsertStatement.executeUpdate();
            videoclipInsertStatement.close();

            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//
//    private static Element[] getElements(Connection connection) throws SQLException {
//        String sql = "SELECT * FROM element";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        ResultSet resultSet = statement.executeQuery();
//
//        // Parsarea rezultatelor și crearea obiectelor Element
//        // În acest exemplu, vom presupune că există doar trei coloane în tabelul element
//        Element[] elements = new Element[resultSet.getFetchSize()];
//        int index = 0;
//        while (resultSet.next()) {
//            String name = resultSet.getString("name");
//            String description = resultSet.getString("description");
//            String creationDate = resultSet.getString("creation_date");
//            Element element = new Element(name, description, LocalDate.parse("12/12/2000", formatter));
//            elements[index++] = element;
//        }
//
//        resultSet.close();
//        statement.close();
//        return elements;
//    }
}