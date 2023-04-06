package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Element implements Comparator<Element> {
    private String name;
    private String description;
    private int size;
    private Date creationDate;
    private ArrayList<Eticheta> etichete;
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Element(String name, String description, int size, Date creationDate) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.creationDate = creationDate;
        this.etichete = new ArrayList<>();
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

    public Date getCreationDate() {
        return creationDate;
    }



    public ArrayList<Eticheta> getEtichete() {
        return etichete;
    }

    public void setEtichete(ArrayList<Eticheta> etichete) {
        this.etichete = etichete;
    }

    public void adaugaEticheta(Eticheta eticheta) {
        this.etichete.add(eticheta);
    }

    public void stergeEticheta(Eticheta eticheta) {
        this.etichete.remove(eticheta);
    }


    public boolean contineEticheta(Eticheta eticheta) {
        return etichete.contains(eticheta);
    }

    @Override
    public int compare(Element e1, Element e2) {
        return e1.getCreationDate().compareTo(e2.getCreationDate());
    }

    @Override
    public String toString() {
        return "name=" + name + '\n' +
                "description=" + description  +
                "\nsize=" + size +
                "\ncreationDate=" + creationDate +
                "\netichete=" + etichete;
    }
}

