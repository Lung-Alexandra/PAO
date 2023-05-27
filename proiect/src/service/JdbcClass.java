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
            ResultSet resultSet = QueryExecutor.executeQuery(sql);

            // Verificarea existenței unui rezultat
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }


            resultSet.close();
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

            ResultSet resultSet = QueryExecutor.executeQuery(sql);

            // Verificarea existenței unui rezultat
            if (resultSet.next()) {
                lastElementId = resultSet.getInt(1);
            }


            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastElementId;
    }

    public static void updateElement(Element element)  {

        try{
            int idElement = getIDByNume("Element",element.getName());
            String sql = "UPDATE Element SET name = ?, description = ? WHERE id = "+idElement;
            PreparedStatement pstmt =  QueryExecutor.executeUpdate(sql);
            pstmt.setString(1, element.getName());
            pstmt.setString(2, element.getDescription());
            pstmt.executeUpdate();
            pstmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public static void updateImagine(Element element)  {
//        try{
//            int idElement = getIDByNume("Element",element.getName());
//            String sql = "UPDATE Imaggine SET name = ?, description = ? WHERE element_id = "+idElement;
//            PreparedStatement pstmt =  QueryExecutor.executeUpdate(sql);
//            pstmt.setString(1, element.getName());
//            pstmt.setString(2, element.getDescription());
//            pstmt.executeUpdate();
//            pstmt.close();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

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

    public static void insertElementEticheta(Element el, Eticheta et) {
        try {


            // Exemplu de inserare a unui album în baza de date
            String sql = "INSERT INTO Element_Eticheta (element_id,eticheta_id) VALUES (?, ?)";

            // Obținerea ultimului ID
            int etId = getIDByNume("Eticheta", et.getName());
            int elId = getIDByNume("Element", el.getName());

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, elId);
            statement.setInt(2, etId);
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

        ResultSet resultSet = QueryExecutor.executeQuery(sql);
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            int size = resultSet.getInt("size");
            LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();


            // Interogarea pentru a citi elementele din baza de date
            String sqlImg = "SELECT * FROM Imagine where element_id = " + id;

            ResultSet resultSetImg = QueryExecutor.executeQuery(sqlImg);
            if (resultSetImg.next()) {
                int idimg = resultSetImg.getInt("id");
                String resolution = resultSetImg.getString("resolution");
                String location = resultSetImg.getString("location");

                String sqlFoto = "SELECT * FROM Fotografie where imagine_id = " + idimg;
                ResultSet resultSetfoto = QueryExecutor.executeQuery(sqlFoto);

                if (resultSetfoto.next()) {
                    String cameraType = resultSetfoto.getString("cameraType");
                    String cameraSettings = resultSetfoto.getString("cameraSettings");
                    Element el = new Fotografie(name, description, size, creationDate, resolution, location, cameraType, cameraSettings);

                    readTags(el, id);
                    return el;
                }


            } else {
                String sqlVid = "SELECT * FROM Videoclip where element_id = " + id;
                ResultSet resultSetVid = QueryExecutor.executeQuery(sqlVid);
                if (resultSetVid.next()) {
                    int duration = resultSetVid.getInt("duration");

                    Element el = new Videoclip(name, description, size, creationDate, duration);

                    readTags(el, id);
                    return el;
                }

            }
        }
        return null;
    }

    private static Eticheta readTag(int idEticheta) throws SQLException {
        // Interogarea pentru a citi elementele din baza de date
        String sql = "SELECT * FROM eticheta where id = " + idEticheta;
        ResultSet resultSet = QueryExecutor.executeQuery(sql);
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            return new Eticheta(name);
        }
        return null;
    }

    private static void readTags(Element el, int id) throws SQLException {
        String sql1 = "SELECT * FROM element_eticheta where element_id = " + id;
        ResultSet resultSet1 = QueryExecutor.executeQuery(sql1);
        while (resultSet1.next()) {
            int id_eticheta = resultSet1.getInt("eticheta_id");

            Eticheta et = readTag(id_eticheta);
            el.addTagToElment(et);
        }
    }

    public static Map<String, Element> readElements() {
        Map<String, Element> elements = new HashMap<>();

        try {
            // Interogarea pentru a citi elementele din baza de date
            String sql = "SELECT * FROM Element";

            ResultSet resultSet = QueryExecutor.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Element element = readElement(id);
                if (element != null)
                    elements.put(element.getName(), element);
            }


            resultSet.close();


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
            ResultSet resultSet = QueryExecutor.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                int idAlbum = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Album al = new Album(name);

                String sql1 = "SELECT * FROM album_element where album_id = " + idAlbum;
                ResultSet resultSet1 = QueryExecutor.executeQuery(sql1);
                while (resultSet1.next()) {
                    int id_element = resultSet1.getInt("element_id");

                    Element el = readElement(id_element);
                    al.addElementToAlbum(el);
                }
                albume.add(al);

            }

            resultSet.close();

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
            ResultSet resultSet = QueryExecutor.executeQuery(sql);

            // Parcurgerea rezultatelor și crearea obiectelor Element corespunzătoare
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                Eticheta et = new Eticheta(name);
                tags.put(et.getName(), et);
            }


            resultSet.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    public static void deleteElement(Element el) {
        try {
            int elementId = getIDByNume("Element", el.getName());
            if (el instanceof Fotografie)
                JdbcClass.deleteImagine(elementId);
            else if (el instanceof Videoclip)
                JdbcClass.deleteVideoclip(elementId);
            deleteElementDinAlbum(elementId);
            deleteElementEticheta(elementId);
            String deleteElementSql = "DELETE FROM Element WHERE id = ?";
            PreparedStatement deleteElementStatement = con.prepareStatement(deleteElementSql);
            deleteElementStatement.setInt(1, elementId);
            deleteElementStatement.executeUpdate();
            deleteElementStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteElementDinAlbum(int elementId) {
        try {
            String deleteElementDinAlbumSql = "DELETE FROM album_element WHERE element_id = ?";
            PreparedStatement deleteElementDinAlbumStatement = con.prepareStatement(deleteElementDinAlbumSql);
            deleteElementDinAlbumStatement.setInt(1, elementId);
            deleteElementDinAlbumStatement.executeUpdate();
            deleteElementDinAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteElementDinAlbum(String elementName) {
        try {
            int elementId = JdbcClass.getIDByNume("Element", elementName);
            String deleteElementDinAlbumSql = "DELETE FROM album_element WHERE element_id = ?";
            PreparedStatement deleteElementDinAlbumStatement = con.prepareStatement(deleteElementDinAlbumSql);
            deleteElementDinAlbumStatement.setInt(1, elementId);
            deleteElementDinAlbumStatement.executeUpdate();
            deleteElementDinAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAlbumElement(int albumId) {
        try {
            String deleteElementDinAlbumSql = "DELETE FROM album_element WHERE album_id = ?";
            PreparedStatement deleteElementDinAlbumStatement = con.prepareStatement(deleteElementDinAlbumSql);
            deleteElementDinAlbumStatement.setInt(1, albumId);
            deleteElementDinAlbumStatement.executeUpdate();
            deleteElementDinAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteElementEticheta(int elementId) {
        try {
            String deleteElementDinAlbumSql = "DELETE FROM element_eticheta WHERE element_id = ?";
            PreparedStatement deleteElementDinAlbumStatement = con.prepareStatement(deleteElementDinAlbumSql);
            deleteElementDinAlbumStatement.setInt(1, elementId);
            deleteElementDinAlbumStatement.executeUpdate();
            deleteElementDinAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteEtichetaElement(int etichetaId) {
        try {
            String deleteElementDinAlbumSql = "DELETE FROM element_eticheta WHERE eticheta_id = ?";
            PreparedStatement deleteElementDinAlbumStatement = con.prepareStatement(deleteElementDinAlbumSql);
            deleteElementDinAlbumStatement.setInt(1, etichetaId);
            deleteElementDinAlbumStatement.executeUpdate();
            deleteElementDinAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteEtichetaElement(Eticheta et) {
        try {
            int etichetaId = getIDByNume("Eticheta", et.getName());
            String deleteElementDinAlbumSql = "DELETE FROM element_eticheta WHERE eticheta_id = ?";
            PreparedStatement deleteElementDinAlbumStatement = con.prepareStatement(deleteElementDinAlbumSql);
            deleteElementDinAlbumStatement.setInt(1, etichetaId);
            deleteElementDinAlbumStatement.executeUpdate();
            deleteElementDinAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteImagine(int elementId) {
        try {
            String getElSql = "select id from imagine WHERE element_id = " + elementId;
            ResultSet resultSet = QueryExecutor.executeQuery(getElSql);
            if (resultSet.next()) {
                int imagineId = resultSet.getInt("id");

                deleteFotografie(imagineId);

                String deleteImagineSql = "DELETE FROM Imagine WHERE id = ?";
                PreparedStatement deleteImagineStatement = con.prepareStatement(deleteImagineSql);
                deleteImagineStatement.setInt(1, imagineId);
                deleteImagineStatement.executeUpdate();
                deleteImagineStatement.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFotografie(int imagineId) {
        try {

            String getImgSql = "select id from Fotografie WHERE imagine_id = " + imagineId;
            ResultSet resultSet = QueryExecutor.executeQuery(getImgSql);
            if (resultSet.next()) {
                int fotografieId = resultSet.getInt("id");
                String deleteFotografieSql = "DELETE FROM Fotografie WHERE id = ?";
                PreparedStatement deleteFotografieStatement = con.prepareStatement(deleteFotografieSql);
                deleteFotografieStatement.setInt(1, fotografieId);
                deleteFotografieStatement.executeUpdate();
                deleteFotografieStatement.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteVideoclip(int elementId) {
        try {
            String getElSql = "select id from videoclip WHERE element_id = " + elementId;
            ResultSet resultSet = QueryExecutor.executeQuery(getElSql);
            if (resultSet.next()) {
                int videoclipId = resultSet.getInt("id");

                String deleteVideoclipSql = "DELETE FROM Videoclip WHERE id = ?";
                PreparedStatement deleteVideoclipStatement = con.prepareStatement(deleteVideoclipSql);
                deleteVideoclipStatement.setInt(1, videoclipId);
                deleteVideoclipStatement.executeUpdate();
                deleteVideoclipStatement.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAlbum(Album al) {
        try {
            int albumId = getIDByNume("Album", al.getName());
            deleteAlbumElement(albumId);
            String deleteAlbumSql = "DELETE FROM Album WHERE id = ?";
            PreparedStatement deleteAlbumStatement = con.prepareStatement(deleteAlbumSql);
            deleteAlbumStatement.setInt(1, albumId);
            deleteAlbumStatement.executeUpdate();
            deleteAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEticheta(Eticheta et) {
        try {
            int tagId = getIDByNume("Eticheta", et.getName());
            deleteEtichetaElement(tagId);
            String deleteAlbumSql = "DELETE FROM Eticheta WHERE id = ?";
            PreparedStatement deleteAlbumStatement = con.prepareStatement(deleteAlbumSql);
            deleteAlbumStatement.setInt(1, tagId);
            deleteAlbumStatement.executeUpdate();
            deleteAlbumStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}