package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import model.*;
import exception.*;


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
        try {
            Element el = elements.get(name);
            if (el != null) {
                elements.remove(name);
                JdbcClass.deleteElement(el);
                AuditService.logAction("removeElement");
            } else {
                throw new ElementNotFoundException("Elementul '" + name + "' nu a fost găsit.");
            }
        } catch (ElementNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }


    // Modificarea  unei imagini sau videoclip existent
    public void updateElement(String name, Element newElement) {
        try {
            Element element = elements.get(name);
            if (element != null) {
                // Eliminăm elementul cu cheia veche
                elements.remove(name);
                // Actualizăm cheia elementului
                element.setName(newElement.getName());
                element.setDescription(newElement.getDescription());
                element.setSize(newElement.getSize());
                element.setCreationDate(newElement.getCreationDate());
                if (newElement instanceof Videoclip) {
                    ((Videoclip) element).setDuration(((Videoclip) newElement).getDuration());

                } else {
                    ((Fotografie) element).setResolution(((Fotografie) newElement).getResolution());
                    ((Fotografie) element).setLocation(((Fotografie) newElement).getLocation());
                    ((Fotografie) element).setCameraType(((Fotografie) newElement).getCameraType());
                    ((Fotografie) element).setCameraSettings(((Fotografie) newElement).getCameraSettings());

                }
                JdbcClass.updateElement(element, name);
                // Adăugăm elementul cu cheia nouă
                elements.put(newElement.getName(), element);
            } else {
                throw new ElementNotFoundException("Elementul '" + name + "' nu a fost găsit.");
            }
            AuditService.logAction("updateElement");
        } catch (ElementNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    public void updateAlbum(String name, String noulNume) {
        try {
            Album alb = getAlbumByName(name);
            if (alb != null) {

                // Actualizăm cheia elementului
                alb.setName(noulNume);

                JdbcClass.updateAlbum(alb, name);


            } else {
                throw new AlbumNotFoundException("Albumul '" + name + "' nu a fost găsit.");
            }
            AuditService.logAction("updateAlbum");
        } catch (AlbumNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    // Vizualizarea unei imagini/videoclip
    public Element viewElement(String name) {
        try {
            AuditService.logAction("viewElement");
            Element element = elements.get(name);
            if (element != null) {
                return element;
            } else {
                throw new ElementNotFoundException("Elementul '" + name + "' nu a fost găsit.");
            }
        } catch (ElementNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
            return null;
        }
    }

    //  Adaugarea unei etichete pentru o imagine/videoclip
    public void addTag(String name, String tag) {
        try {
            Element el = elements.get(name);
            if (el != null) {
                Eticheta et = new Eticheta(tag);
                if (el.contineEticheta(et)) {
                    System.out.println("Eticheta deja existenta la element.");
                } else {
                    if (!tags.containsKey(et.getName())) {
                        JdbcClass.insertEticheta(et);
                    }
                    //pentru a evita verificarea manuală a
                    // existenței etichetei înainte de a o
                    // adăuga în map
                    tags.putIfAbsent(et.getName(), et);
                    el.addTagToElment(et);

                    JdbcClass.insertElementEticheta(el, et);
                    System.out.println("Eticheta adaugata cu succes.");
                }
                AuditService.logAction("addTag");
            } else {
                throw new ElementNotFoundException("Elementul '" + name + "' nu a fost găsit.");
            }
        } catch (ElementNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());

        }
    }
    //  Stergerea unei etichete pentru o imagine/videoclip

    public void removeTag(String name, String tag) {
        Element el = elements.get(name);
        if (el != null) {
            Eticheta et = new Eticheta(tag);
            if (el.contineEticheta(et)) {
                el.stergeEticheta(et);
                JdbcClass.deleteEtichetaElement(et);
                System.out.println("Eticheta a fost stearsa cu succes.");
            } else {
                System.out.println("Eticheta '" + tag + "' nu a fost gasita pentru elementul '" + name + "'.");
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
        try {
            Album album = getAlbumByName(albumName);
            if (album != null) {
                Element element = elements.get(elementName);
                if (element != null) {
                    List<Element> elementeAlbum = album.getAlbumElements();
                    if(!elementeAlbum.contains(element)) {
                        album.addElementToAlbum(element);
                        JdbcClass.insertAlbumElement(album, element);
                    }else{
                        System.out.println("Element deja existent in ablum!");
                    }
                } else {
                    System.out.println("Elementul nu exista!");
                }
            } else {
                throw new AlbumNotFoundException("Albumul '" + albumName + "' nu a fost găsit.");
            }

        } catch (AlbumNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
        AuditService.logAction("addElementToAlbum");
    }

    // Stergerea unei imagini/videoclip din album
    public void removeElementFromAlbum(String albumName, String elementName) {
        try {
            Album album = getAlbumByName(albumName);
            if (album != null) {
                Element element = elements.get(elementName);
                if (element != null) {  List<Element> elementeAlbum = album.getAlbumElements();
                    if(elementeAlbum.contains(element)) {
                        album.removeElement(elementName);
                        JdbcClass.deleteElementDinAlbum(elementName);
                    }else{
                        System.out.println("Elementul " + elementName + " nu exista in albumul " + albumName);
                    }
                } else {
                    System.out.println("Elementul nu exista!");
                }
            } else {
                throw new AlbumNotFoundException("Albumul '" + albumName + "' nu a fost găsit.");
            }
        } catch (AlbumNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
        AuditService.logAction("removeElementFromAlbum");
    }

    // Vizualizarea continutului unui album
    public void viewAlbumContent(String albumName) {
        try {
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
                throw new AlbumNotFoundException("Albumul '" + albumName + "' nu a fost găsit.");
            }

        } catch (AlbumNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
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
        Album alb = getAlbumByName(albumName);
        if (alb != null) {
            System.out.println("Album exista deja");
        } else {
            JdbcClass.insertAlbum(new Album(albumName));
            albums.add(new Album(albumName));
        }
    }

    public List<Album> viewAllAlbums() {
        AuditService.logAction("viewAllAlbums");
        return new ArrayList<>(albums);
    }

    public void deleteAlbum(String albumName) {
        try {
            Album al = getAlbumByName(albumName);
            if (al != null) {
                albums.remove(al);
                JdbcClass.deleteAlbum(al);
                System.out.println("Album sters cu succes.");
                AuditService.logAction("deleteAlbum");
            } else {
                throw new AlbumNotFoundException("Albumul '" + albumName + "' nu a fost găsit.");
            }

        } catch (AlbumNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    //17
    public int sizeOfGalery() {
        AuditService.logAction("sizeOfGalery");
        return elements.values().stream()
                .mapToInt(Element::getSize)
                .sum();
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