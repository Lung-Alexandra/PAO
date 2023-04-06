package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album {
    private String nume;
    private List<Element> elemente;

    public Album(String nume) {
        this.nume = nume;
        this.elemente = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public void adaugaElement(Element element) {
        elemente.add(element);
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setElemente(List<Element> elemente) {
        this.elemente = elemente;
    }

    public List<Element> getElemente() {
        return elemente;
    }

    public void removeElement(String elementName) {
        Element elementToRemove = null;
        for (Element element : elemente) {
            if (element.getName().equals(elementName)) {
                elementToRemove = element;
                break;
            }
        }
        if (elementToRemove != null) {
            elemente.remove(elementToRemove);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(nume, album.nume) && Objects.equals(elemente, album.elemente);
    }

    @Override
    public String toString() {
        return "Album{" +
                "\nnume=" + nume  +
                "\nelemente=" + elemente +
                '}';
    }

    public void addElement(Element element) {
        elemente.add(element);
    }
}
