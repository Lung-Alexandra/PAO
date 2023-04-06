package model;

import java.util.Comparator;

public class ElementComparator implements Comparator<Element> {
    private final String sortBy;

    public ElementComparator(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public int compare(Element e1, Element e2) {
        return switch (sortBy) {
            case "name" -> e1.getName().compareTo(e2.getName());
            case "date" -> e1.getCreationDate().compareTo(e2.getCreationDate());
            case "size" -> Integer.compare(e1.getSize(), e2.getSize());
            default -> throw new IllegalArgumentException("Criteriu invalid");
        };
    }
}