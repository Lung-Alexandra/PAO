package util;
import model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Filtru {
    private Filtru() {
    }

    public static List<Element> filtrareDupaNume(List<Element> elem, String nume) {
        return elem.stream()
                .filter(el -> el.getName().toLowerCase().contains(nume.toLowerCase()))
                .collect(Collectors.toList());
    }



    public static List<Element> filtrareDupaData(List<Element> elem, LocalDate dataInceput, LocalDate dataSfarsit) {
        return elem.stream()
                .filter(el -> el.getCreationDate().isAfter(dataInceput) && el.getCreationDate().isBefore(dataSfarsit))
                .collect(Collectors.toList());
    }


    public static List<Element> filtrareDupaDimensiune(List<Element> elem, int dimensiuneMinima, int dimensiuneMaxima) {
        return elem.stream()
                .filter(el -> el.getSize() >= dimensiuneMinima && el.getSize() <= dimensiuneMaxima)
                .collect(Collectors.toList());
    }
    public static List<Element> filtrareDupaEticheta(List<Element> elem, Eticheta eticheta) {
        return elem.stream()
                .filter(el -> el.contineEticheta(eticheta))
                .collect(Collectors.toList());
    }

}
