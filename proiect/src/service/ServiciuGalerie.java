package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import model.*;


public class ServiciuGalerie {
    private final Map<String, Element> elements;
    private final Map<String, Eticheta> tags;
    private final List<Album> albums;

    public ServiciuGalerie() {
        this.elements = JdbcClass.readElements();
        this.albums = JdbcClass.readAlbums();
        this.tags = JdbcClass.readTags();
    }


    // Adaugarea unei noi imagini/videoclip in galerie
    public void addElement(String name, String description, int size, String type, int duration, LocalDate dataCreare, String resolutie, String locatie, String tipcamera, String setaricamera) {
        Element element = null;
        if (type.equals("vid")) {
            element = new Videoclip(name, description, size, dataCreare, duration);
            JdbcClass.insertVideoclip((Videoclip) element);
        } else if (type.equals("img")) {
            element = new Fotografie(name, description, size, dataCreare, resolutie, locatie, tipcamera, setaricamera);
            JdbcClass.insertFotografie((Fotografie) element);
        }
        if (element != null) {
            elements.put(element.getName(), element);
        }
        AuditService.logAction("addElement");
    }

    // Stergerea unei imagini sau videoclip din galerie
    public void removeElement(String name) {
        elements.remove(name);
        AuditService.logAction("removeElement");
    }


    // Modificarea numelui sau descrierii unei imagini sau videoclip existente
    public void updateElement(String name, String newName, String newDescription) {
        Element element = elements.get(name);
        if (element != null) {
            // Eliminăm elementul cu cheia veche
            elements.remove(name);
            // Actualizăm cheia elementului
            element.setName(newName);
            element.setDescription(newDescription);
            // Adăugăm elementul cu cheia nouă
            elements.put(newName, element);
        }
        AuditService.logAction("updateElement");
    }

    // Vizualizarea unei imagini/videoclip
    public Element viewElement(String name) {
        AuditService.logAction("viewElement");
        return elements.get(name);
    }

    //  Adaugarea unei etichete pentru o imagine/videoclip
    public void addTag(String name, String tag) {
        Element el = elements.get(name);
        if (el != null) {
            Eticheta et = new Eticheta(tag);
            //pentru a evita verificarea manuală a
            // existenței etichetei înainte de a o
            // adăuga în map
            tags.putIfAbsent(et.getName(), et);
            if (el.contineEticheta(et)) {
                System.out.println("Eticheta deja existenta la element.");
            } else {
                el.addTagToElment(et);
                JdbcClass.insertEticheta(et);
                JdbcClass.insertElementEticheta(el,et);
                System.out.println("Eticheta adaugata cu succes.");
            }
        } else {
            System.out.println("Elementul nu exista!");
        }
        AuditService.logAction("addTag");
    }
    //  Stergerea unei etichete pentru o imagine/videoclip

    public void removeTag(String name, String tag) {
        Element el = elements.get(name);
        if (el != null) {
            Eticheta et = new Eticheta(tag);
            if (el.contineEticheta(et)) {
                el.stergeEticheta(et);
                System.out.println("Eticheta a fost stearsa cu succes.");
            } else {
                System.out.println("Tag '" + tag + "' not found for element '" + name + "'.");
            }
        } else {
            System.out.println("Elementul nu exista!");
        }
        AuditService.logAction("removeTag");
    }

    // Vizualizarea imaginilor/videoclipurilor cu un anumit tag
    public void viewElementsByTag(String tag) {
        List<Element> result = new ArrayList<>();
        for (Element element : elements.values()) {
            if (element.contineEticheta(new Eticheta(tag))) {
                result.add(element);
            }
        }
        if (!result.isEmpty()) {
            for (Element el : result) {
                System.out.println(el);
                System.out.println();
            }
        } else {
            System.out.println("Nu exista elemente cu eticheta " + tag + ".");
        }

        AuditService.logAction("viewElementsByTag");
    }


    // Sortarea imaginilor/videoclipurilor dupa nume, data, dimensiune sau alte criterii
    public List<Element> sortElements(String sortBy) {
        List<Element> result = new ArrayList<>(elements.values());
        ElementComparator comparator = new ElementComparator(sortBy);
        result.sort(comparator);
        AuditService.logAction("sortElements");
        return result;
    }

    // Adaugarea unei imagini/videoclip in album
    public void addElementToAlbum(String albumName, String elementName) {
        Album album = getAlbumByName(albumName);
        if (album != null) {
            Element element = elements.get(elementName);
            if (element != null) {
                album.addElementToAlbum(element);
                JdbcClass.insertAlbumElement(album,element);
            } else {
                System.out.println("Elementul nu exista!");
            }
        } else {
            System.out.println("Albumul nu exista!");
        }
        AuditService.logAction("addElementToAlbum");
    }

    // Stergerea unei imagini/videoclip din album
    public void removeElementFromAlbum(String albumName, String elementName) {
        Album album = getAlbumByName(albumName);
        if (album != null) {
            Element element = elements.get(elementName);
            if (element != null) {
                album.removeElement(elementName);
            } else {
                System.out.println("Elementul nu exista!");
            }
        } else {
            System.out.println("Albumul nu exista!");
        }
        AuditService.logAction("removeElementFromAlbum");
    }

    // Vizualizarea continutului unui album
    public void viewAlbumContent(String albumName) {
        Album album = getAlbumByName(albumName);
        if (album != null) {
            List<Element> elementeAlbum = album.getAlbumElements();
            if (!elementeAlbum.isEmpty()) {
                System.out.println("Elementele din albumul " + albumName + " sunt: ");
                for (Element element : elementeAlbum) {
                    System.out.println(element.toString());
                }
            } else {
                System.out.println("Albumul " + albumName + " este gol.");
            }
        } else {
            System.out.println("Albumul nu exista!");
        }
        AuditService.logAction("viewAlbumContent");

    }

    // Vizualizarea listei de imagini sau videoclipuri
    public List<Element> viewAllElements() {
        AuditService.logAction("viewAllElements");
        return new ArrayList<>(elements.values());
    }

    // Metoda privata pentru a gasi un album dupa nume
    private Album getAlbumByName(String albumName) {
        AuditService.logAction("getAlbumByName");

        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }


    // Crearea unui album de imagini/videoclipuri
    public void createAlbum(String albumName) {
        AuditService.logAction("createAlbum");
        JdbcClass.insertAlbum(new Album(albumName));
        albums.add(new Album(albumName));
    }

    public List<Album> viewAllAlbums() {
        AuditService.logAction("viewAllAlbums");
        return new ArrayList<>(albums);
    }
    public void deleteAlbum(String albumName) {
        Album al = getAlbumByName(albumName);
        if (al == null)
            System.out.println("Albumul nu a fost gasit.");

        albums.remove(al);
        System.out.println("Album sters cu succes.");
        AuditService.logAction("deleteAlbum");
    }

    //citire data
    public LocalDate readLocalData(String date) {
        AuditService.logAction("readLocalData");
        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            data = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Data introdusa este invalida. Incercati din nou:");
        }
        return data;
    }

}