package service;

import java.util.*;

import model.*;


public class ServiciuGalerie {
    private Map<String, Element> elements = new HashMap<>();
    private Map<String, Eticheta> tags = new HashMap<>();
    private List<Album> albums = new ArrayList<>();

    public ServiciuGalerie() {
    }

    public ServiciuGalerie(Map<String, Element> elements, Map<String, Eticheta> tags, List<Album> albums) {
        this.elements = elements;
        this.tags = tags;
        this.albums = albums;
    }

    // Adaugarea unei noi imagini/videoclip in galerie
    public void addElement(String name, String description, int size, String type, int duration, Date dataCreare, String resolutie, String locatie, String tipcamera, String setaricamera) {
        Element element = null;
        if (type.equals("vid")) {
            element = new Videoclip(name, description, size, dataCreare, duration);
        } else if (type.equals("img")) {
            element = new Fotografie(name, description, size, dataCreare, resolutie, locatie, tipcamera, setaricamera);
        }
        if (element != null) {
            elements.put(element.getName(), element);
        }
    }

    // Stergerea unei imagini sau videoclip din galerie
    public void removeElement(String name) {
        elements.remove(name);
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
    }

    // Vizualizarea unei imagini/videoclip
    public Element viewElement(String name) {
        return elements.get(name);
    }

    //  Adaugarea unei etichete pentru o imagine/videoclip
    public void addTag(String name, String tag) {
        Element el = elements.get(name);
        if (el != null) {
            Eticheta et = new Eticheta(tag);
            if (!tags.containsValue(et))
                tags.put(et.getNume(), et);
            if (!el.contineEticheta(et)) {
                el.adaugaEticheta(et);
                System.out.println("Eticheta adaugata cu succes.");
            } else {
                System.out.println("Eticheta deja existenta la element.");
            }
        } else {
            System.out.println("Elementul nu exista!");
        }

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


    }


    // Sortarea imaginilor/videoclipurilor dupa nume, data, dimensiune sau alte criterii
    public List<Element> sortElements(String sortBy) {
        List<Element> result = new ArrayList<>(elements.values());
        ElementComparator comparator = new ElementComparator(sortBy);
        result.sort(comparator);
        return result;
    }

    // Adaugarea unei imagini/videoclip in album
    public void addElementToAlbum(String albumName, String elementName) {
        Album album = getAlbumByName(albumName);
        if (album != null) {
            Element element = elements.get(elementName);
            if (element != null) {
                album.addElement(element);
            } else {
                System.out.println("Elementul nu exista!");
            }
        } else {
            System.out.println("Albumul nu exista!");
        }
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
    }

    // Vizualizarea continutului unui album
    public void viewAlbumContent(String albumName) {
        Album album = getAlbumByName(albumName);
        if (album != null) {
            List<Element> elementeAlbum = album.getElemente();
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


    }

    // Vizualizarea listei de imagini sau videoclipuri
    public List<Element> viewAllElements() {
        return new ArrayList<>(elements.values());
    }

    // Metoda privata pentru a gasi un album dupa nume
    private Album getAlbumByName(String albumName) {
        for (Album album : albums) {
            if (album.getNume().equals(albumName)) {
                return album;
            }
        }
        return null;
    }


    // Crearea unui album de imagini/videoclipuri
    public void createAlbum(String albumName) {
        albums.add(new Album(albumName));
    }

    public void deleteAlbum(String albumName) {
        Album al = getAlbumByName(albumName);
        if (al == null)
            System.out.println("Albumul nu a fost gasit.");

        albums.remove(al);
        System.out.println("Album sters cu succes.");

    }

}