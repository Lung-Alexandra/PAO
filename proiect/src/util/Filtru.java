package util;
import model.*;
import java.util.*;
public class Filtru {
    public static List<Element> filtrareDupaNume(List<Element> elem, String nume) {
        List<Element> rezultat = new ArrayList<>();
        for (Element el : elem) {
            if (el.getName().toLowerCase().contains(nume.toLowerCase())) {
                rezultat.add(el);
            }
        }
        return rezultat;
    }

    public static List<Element> filtrareDupaEticheta(List<Element> elem, Eticheta eticheta) {
        List<Element> rezultat = new ArrayList<>();
        for (Element el : elem) {
            if (el.contineEticheta(eticheta)) {
                rezultat.add(el);
            }
        }
        return rezultat;
    }

    public static List<Element> filtrareDupaData(List<Element> elem, Date dataInceput, Date dataSfarsit) {
        List<Element> rezultat = new ArrayList<>();
        for (Element el : elem) {
            if (el.getCreationDate().after(dataInceput) && el.getCreationDate().before(dataSfarsit)) {
                rezultat.add(el);
            }
        }
        return rezultat;
    }

    public static List<Element> filtrareDupaDimensiune(List<Element> elem, int dimensiuneMinima, int dimensiuneMaxima) {
        List<Element> rezultat = new ArrayList<>();
        for (Element el : elem) {
            if (el.getSize() >= dimensiuneMinima && el.getSize() <= dimensiuneMaxima) {
                rezultat.add(el);
            }
        }
        return rezultat;
    }
}
