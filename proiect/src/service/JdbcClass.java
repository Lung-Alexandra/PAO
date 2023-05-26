package service;

import dataBase.Db;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcClass {

    private static final Db database = Db.getDatabase();
    private static final Connection con = database.getConnection();

    public static int getIDByNume(String tableName, String name) throws SQLException {
        int id = 0;
        try {
            // Obținerea ultimului id din tabela Element
            String sql = "SELECT * FROM " + tableName + " Where name = " + "\"" + name + "\"";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Verificarea existenței unui rezultat
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }


            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int getLastId(String tableName) throws SQLException {
        int lastElementId = 0;

        try {


            // Obținerea ultimului id din tabela Element
            String sql = "SELECT MAX(id) FROM " + tableName;
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Verificarea existenței unui rezultat
            if (resultSet.next()) {
                lastElementId = resultSet.getInt(1);
            }


            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastElementId;
    }

    public static void insertElement(Element element) throws SQLException {
        try {

            // Exemplu de inserare a unui element în baza de date
            String sql = "INSERT INTO Element (id, name, description,size, creationDate) VALUES (?, ?, ?, ?, ?)";


            // Obținerea ultimului ID
            int lastId = getLastId("Element") + 1;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, String.valueOf(lastId));
            statement.setString(2, element.getName());
            statement.setString(3, element.getDescription());
            statement.setObject(4, element.getSize());
            statement.setObject(5, element.getCreationDate());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertImagine(Imagine imagine) throws SQLException {
        try {

            Element el = new Element(imagine.getName(), imagine.getDescription(), imagine.getSize(), imagine.getCreationDate());
            insertElement(el);
            int lastElementId = getLastId("Element");
            int lastId = getLastId("Imagine") + 1;
            // Inserting the Imagine with the last inserted id
            String imagineInsertSql = "INSERT INTO Imagine (id,element_id, resolution, location) VALUES (?,?, ?, ?)";
            PreparedStatement imagineInsertStatement = con.prepareStatement(imagineInsertSql);
            imagineInsertStatement.setInt(1, lastId);
            imagineInsertStatement.setInt(2, lastElementId);
            imagineInsertStatement.setString(3, imagine.getResolution());
            imagineInsertStatement.setString(4, imagine.getLocation());
            imagineInsertStatement.executeUpdate();
            imagineInsertStatement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertFotografie(Fotografie fotografie) {
        try {


            Imagine el = new Imagine(fotografie.getName(), fotografie.getDescription(), fotografie.getSize(), fotografie.getCreationDate(), fotografie.getResolution(), fotografie.getLocation());
            insertImagine(el);
            int lastElementId = getLastId("Imagine");
            int lastId = getLastId("Fotografie") + 1;
            // Inserting the Fotografie with the last inserted id
            String fotografieInsertSql = "INSERT INTO Fotografie (id,imagine_id, cameraType, cameraSettings) VALUES (?, ?,?, ?)";
            PreparedStatement fotografieInsertStatement = con.prepareStatement(fotografieInsertSql);
            fotografieInsertStatement.setInt(1, lastId);
            fotografieInsertStatement.setInt(2, lastElementId);
            fotografieInsertStatement.setString(3, fotografie.getCameraType());
            fotografieInsertStatement.setString(4, fotografie.getCameraSettings());
            fotografieInsertStatement.executeUpdate();
            fotografieInsertStatement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertVideoclip(Videoclip videoclip) {
        try {


            Element el = new Element(videoclip.getName(), videoclip.getDescription(), videoclip.getSize(), videoclip.getCreationDate());
            insertElement(el);

            int lastElementId = getLastId("Element");
            int lastId = getLastId("Imagine") + 1;
            // Inserting the Videoclip with the last inserted id
            String videoclipInsertSql = "INSERT INTO Videoclip (id,element_id, duration) VALUES (?,?, ?)";
            PreparedStatement videoclipInsertStatement = con.prepareStatement(videoclipInsertSql);
            videoclipInsertStatement.setInt(1, lastId);
            videoclipInsertStatement.setInt(2, lastElementId);
            videoclipInsertStatement.setInt(3, videoclip.getDuration());
            videoclipInsertStatement.executeUpdate();
            videoclipInsertStatement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAlbum(Album album) {
        try {


            // Exemplu de inserare a unui album în baza de date
            String sql = "INSERT INTO Album (id, name) VALUES (?, ?)";

            // Obținerea ultimului ID
            int lastId = getLastId("Album") + 1;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastId);
            statement.setString(2, album.getName());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAlbumElement(Album al, Element el) {
        try {


            // Exemplu de inserare a unui album în baza de date
            String sql = "INSERT INTO album_element (album_id, element_id) VALUES (?, ?)";

            // Obținerea ultimului ID
            int alId = getIDByNume("Album", al.getName());
            int elId = getIDByNume("Element", el.getName());

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, alId);
            statement.setInt(2, elId);
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertEticheta(Eticheta eticheta) {
        try {


            // Exemplu de inserare a unei etichete în baza de date
            String sql = "INSERT INTO Eticheta (id, name) VALUES (?, ?)";

            // Obținerea ultimului ID
            int lastId = getLastId("Eticheta") + 1;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastId);
            statement.setString(2, eticheta.getName());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Element readElement(int id) throws SQLException {
        // Interogarea pentru a citi elementele din baza de date
        String sql = "SELECT * FROM Element where id = " + id;
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            int size = resultSet.getInt("size");
            LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();


            // Interogarea pentru a citi elementele din baza de date
            String sqlImg = "SELECT * FROM Imagine where element_id = " + id;
            Statement statementImg = con.createStatement();
            ResultSet resultSetImg = statementImg.executeQuery(sqlImg);
            if (resultSetImg.next()) {
                int idimg = resultSetImg.getInt("id");
                String resolution = resultSetImg.getString("resolution");
                String location = resultSetImg.getString("location");

                String sqlFoto = "SELECT * FROM Fotografie where imagine_id = " + idimg;
                Statement statementfoto = con.createStatement();
                ResultSet resultSetfoto = statementfoto.executeQuery(sqlFoto);

                if (resultSetfoto.next()) {
                    String cameraType = resultSetfoto.getString("cameraType");
                    String cameraSettings = resultSetfoto.getString("cameraSettings");
                    return new Fotografie(name, description, size, creationDate, resolution, location, cameraType, cameraSettings);
                }


            } else {
                String sqlVid = "SELECT * FROM Videoclip where element_id = " + id;
                Statement statementVid = con.createStatement();
                ResultSet resultSetVid = statementVid.executeQuery(sqlVid);
                if (resultSetVid.next()) {
                    int duration = resultSetVid.getInt("duration");

                    return new Videoclip(name, description, size, creationDate, duration);
                }

            }
        }
        return null;
    }

    public static Map<String, Element> readElements() {
        Map<String, Element> elements = new HashMap<>();

        try {
            // Interogarea pentru a citi elementele din baza de date
            String sql = "SELECT * FROM Element";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Element element = readElement(id);
                if (element != null)
                    elements.put(element.getName(), element);
            }


            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elements;
    }

    public static List<Album> readAlbums() {
        List<Album> albume = new ArrayList<>();

        try {

            // Interogarea pentru a citi elementele din baza de date
            String sql = "SELECT * FROM Album";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                int idAlbum = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Album al = new Album(name);

                String sql1 = "SELECT * FROM album_element where album_id = " + idAlbum;
                Statement statement1 = con.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(sql1);
                while (resultSet1.next()) {
                    int id_element = resultSet1.getInt("element_id");

                    Element el = readElement(id_element);
                    al.addElementToAlbum(el);
                }
                albume.add(al);

            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return albume;
    }

    public static Map<String, Eticheta> readTags() {
        Map<String, Eticheta> tags = new HashMap<>();

        try {

            // Interogarea pentru a citi elementele din baza de date
            String sql = "SELECT * FROM eticheta";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                Eticheta et = new Eticheta(name);
                tags.put(et.getName(), et);
            }


            resultSet.close();
            statement.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

}