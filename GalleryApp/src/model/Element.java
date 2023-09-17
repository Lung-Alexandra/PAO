package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Element implements Comparator<Element> {
    private String name;
    private String description;
    private int size;
    private LocalDate creationDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ArrayList<Eticheta> elementTags;
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Element(String name, String description, int size, LocalDate creationDate) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.creationDate = creationDate;
        this.elementTags = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }



    public ArrayList<Eticheta> getElementTags() {
        return elementTags;
    }

    public void setElementTags(ArrayList<Eticheta> elementTags) {
        this.elementTags = elementTags;
    }

    public void addTagToElment(Eticheta eticheta) {
        this.elementTags.add(eticheta);
    }

    public void stergeEticheta(Eticheta eticheta) {
        this.elementTags.remove(eticheta);
    }


    public boolean contineEticheta(Eticheta eticheta) {
        return elementTags.contains(eticheta);
    }

    @Override
    public int compare(Element e1, Element e2) {
        return e1.getCreationDate().compareTo(e2.getCreationDate());
    }

    @Override
    public String toString() {
        return "   name = " + name + '\n' +
                "   description = " + description  +
                "\n   size = " + size +
                "\n   creationDate = " + formatter.format(creationDate) +
                "\n   etichete = " + elementTags;
    }
}

