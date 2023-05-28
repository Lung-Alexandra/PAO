package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album {
    private String name;
    private List<Element> albumElements;

    public Album(String name) {
        this.name = name;
        this.albumElements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addElementToAlbum(Element element) {
        albumElements.add(element);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbumElements(List<Element> albumElements) {
        this.albumElements = albumElements;
    }

    public List<Element> getAlbumElements() {
        return albumElements;
    }

    public void removeElement(String elementName) {
        Element elementToRemove = null;
        for (Element element : albumElements) {
            if (element.getName().equals(elementName)) {
                elementToRemove = element;
                break;
            }
        }
        if (elementToRemove != null) {
            albumElements.remove(elementToRemove);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(name, album.name) && Objects.equals(albumElements, album.albumElements);
    }

    @Override
    public String toString() {
        return "Album" +
                "\n   nume = " + name +
                "\n   elemente = " + albumElements +
                "\n";
    }


}
