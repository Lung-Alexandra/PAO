package service;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class JdbcClass {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/galeriedefotografi";

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JdbcClass() {
    }

    public static int getLastId(String tableName) throws SQLException {
        int lastElementId = 0;

        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Obținerea ultimului id din tabela Element
            String sql = "SELECT MAX(id) FROM " + tableName;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Verificarea existenței unui rezultat
            if (resultSet.next()) {
                lastElementId = resultSet.getInt(1);
            }

            // Închiderea conexiunii JDBC
            resultSet.close();
            statement.close();
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
            int lastId = getLastId("Element") + 1;
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
            int lastElementId = getLastId("Element");
            int lastId = getLastId("Imagine") + 1;
            // Inserting the Imagine with the last inserted id
            String imagineInsertSql = "INSERT INTO Imagine (id,element_id, resolution, location) VALUES (?,?, ?, ?)";
            PreparedStatement imagineInsertStatement = connection.prepareStatement(imagineInsertSql);
            imagineInsertStatement.setInt(1, lastId);
            imagineInsertStatement.setInt(2, lastElementId);
            imagineInsertStatement.setString(3, imagine.getResolution());
            imagineInsertStatement.setString(4, imagine.getLocation());
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
            int lastElementId = getLastId("Imagine");
            int lastId = getLastId("Fotografie") + 1;
            // Inserting the Fotografie with the last inserted id
            String fotografieInsertSql = "INSERT INTO Fotografie (id,imagine_id, cameraType, cameraSettings) VALUES (?, ?,?, ?)";
            PreparedStatement fotografieInsertStatement = connection.prepareStatement(fotografieInsertSql);
            fotografieInsertStatement.setInt(1, lastId);
            fotografieInsertStatement.setInt(2, lastElementId);
            fotografieInsertStatement.setString(3, fotografie.getCameraType());
            fotografieInsertStatement.setString(4, fotografie.getCameraSettings());
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

            int lastElementId = getLastId("Element");
            int lastId = getLastId("Imagine") + 1;
            // Inserting the Videoclip with the last inserted id
            String videoclipInsertSql = "INSERT INTO Videoclip (id,element_id, duration) VALUES (?,?, ?)";
            PreparedStatement videoclipInsertStatement = connection.prepareStatement(videoclipInsertSql);
            videoclipInsertStatement.setInt(1, lastId);
            videoclipInsertStatement.setInt(2, lastElementId);
            videoclipInsertStatement.setInt(3, videoclip.getDuration());
            videoclipInsertStatement.executeUpdate();
            videoclipInsertStatement.close();

            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAlbum(Album album) throws SQLException {
        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Exemplu de inserare a unui album în baza de date
            String sql = "INSERT INTO Album (id, nume) VALUES (?, ?)";

            // Obținerea ultimului ID
            int lastId = getLastId("Album") + 1;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lastId);
            statement.setString(2, album.getNume());
            statement.executeUpdate();
            statement.close();

            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertEticheta(Eticheta eticheta) throws SQLException {
        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Exemplu de inserare a unei etichete în baza de date
            String sql = "INSERT INTO Eticheta (id, nume) VALUES (?, ?)";

            // Obținerea ultimului ID
            int lastId = getLastId("Eticheta") + 1;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lastId);
            statement.setString(2, eticheta.getNume());
            statement.executeUpdate();
            statement.close();

            // Închiderea conexiunii JDBC
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Element> readElements() throws SQLException {
        Map<String, Element> elements = new HashMap<>();

        try {
            // Configurarea conexiunii JDBC
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Interogarea pentru a citi elementele din baza de date
            String sql = "SELECT * FROM Element";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int size = resultSet.getInt("size");
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();


                // Interogarea pentru a citi elementele din baza de date
                String sqlImg = "SELECT * FROM Imagine where element_id = " + id;
                Statement statementImg = connection.createStatement();
                ResultSet resultSetImg = statementImg.executeQuery(sqlImg);
                if (resultSetImg.next()) {
                    int idimg = resultSetImg.getInt("id");
                    String resolution = resultSetImg.getString("resolution");
                    String location = resultSetImg.getString("location");

                    String sqlFoto = "SELECT * FROM Fotografie where imagine_id = " + idimg;
                    Statement statementfoto = connection.createStatement();
                    ResultSet resultSetfoto = statementfoto.executeQuery(sqlFoto);

                    if (resultSetfoto.next()) {
                        String cameraType = resultSetfoto.getString("cameraType");
                        String cameraSettings = resultSetfoto.getString("cameraSettings");
                        Element element = new Fotografie(name, description, size, creationDate, resolution, location, cameraType, cameraSettings);
                        elements.put(element.getName(), element);
                    }


                }else {
                    String sqlVid = "SELECT * FROM Videoclip where element_id = " + id;
                    Statement statementVid = connection.createStatement();
                    ResultSet resultSetVid = statementVid.executeQuery(sqlVid);
                    if (resultSetVid.next()) {
                        int duration = resultSetVid.getInt("duration");

                        Element element = new Videoclip(name, description, size, creationDate,duration);
                        elements.put(element.getName(), element);
                    }

                }
            }

            // Închiderea conexiunii JDBC
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elements;
    }
}