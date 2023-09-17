package menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Element;
import model.Eticheta;
import model.Fotografie;
import model.Videoclip;
import service.*;
import util.Filtru;

public class MeniuElemente implements Meniu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ServiciuGalerie serviciuGalerie = new ServiciuGalerie();
    private static boolean exit1 = false;

    public static void setExit1(boolean exit1) {
        MeniuElemente.exit1 = exit1;
    }

    public static void afisareMeniuElemente() {
        System.out.println();
        System.out.println("=== Meniu Elemente ===");
        System.out.println("1. Afiseaza toate imaginile si videoclipurile");
        System.out.println("2. Adauga imagine/videoclip");
        System.out.println("3. Modifica imagine/videoclip");
        System.out.println("4. Cauta imagine/videoclip");
        System.out.println("5. Sterge imagine/videoclip");
        System.out.println("6. Filtrare fotografii/videoclipuri dupa diferite criterii");
        System.out.println("7. Sortare fotografii/videoclipuri dupa diferite criterii");
        System.out.println("0. Inapoi");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    public static void gestioneazaOptiuneElemente(String optiune) {

        switch (optiune) {
            case "1" -> {
                afiseazaElemente();
                AuditService.logAction("afiseazaElemente");
            }
            case "2" -> {
                adaugaElement();
                AuditService.logAction("adaugaElement");
            }
            case "3" -> {
                actualizeazaElement();
                AuditService.logAction("actualizeazaElement");
            }
            case "4" -> {
                cautaElement();
                AuditService.logAction("cautaElement");
            }
            case "5" -> {
                stergeElement();
                AuditService.logAction("stergeElement");
            }
            case "6" -> {
                filtrareElemente();
                AuditService.logAction("filtrareElemente");
            }   case "7" -> {
                sortareElemente();
                AuditService.logAction("sortareElemente");
            }
            case "0" -> exit1 = true; // Ieșire din metoda curentă pentru a reveni la meniul principal

            default -> System.out.println("Optiune invalida.");
        }

    }

    @Override
    public void run() {

        while (!exit1) {
            afisareMeniuElemente(); // Apelăm metoda specifică MeniuElemente
            String optiune = scanner.nextLine();
            gestioneazaOptiuneElemente(optiune); // Apelăm metoda specifică MeniuElemente
        }

    }

    // 1
    private static void afiseazaElemente() {
        List<Element> elements = serviciuGalerie.viewAllElements();
        if (!elements.isEmpty()) {
            elements.forEach(System.out::println);
        } else {
            System.out.println("Nu exista elemente in galerie!");
        }
        System.out.println();
    }


    // 2
    private static void adaugaElement() {
        System.out.println("Introduceti numele:");
        String nume = scanner.nextLine().trim();
        System.out.println("Introduceti descrierea:");
        String descriere = scanner.nextLine().trim();
        System.out.print("Data crearii(dd/MM/yyyy): ");
        LocalDate data = null;
        while (data == null) {
            data = serviciuGalerie.readLocalData(scanner.nextLine());
        }
        System.out.println("Introduceti dimensiunea:");
        int dimensiune = scanner.nextInt();
        scanner.nextLine(); // consuma newline
        boolean ok = true;
        while (ok) {
            System.out.println("Introduceti tipul (img/vid):");
            String tip = scanner.nextLine().trim();
            if (tip.equals("img")) {
                System.out.println("Introduceti resolutia:");
                String resolutie = scanner.nextLine().trim();
                System.out.println("Introduceti locatia:");
                String locatie = scanner.nextLine().trim();
                System.out.println("Introduceti tipul de camera:");
                String tipCamera = scanner.nextLine().trim();
                System.out.println("Introduceti setarile camerei:");
                String setariCamera = scanner.nextLine().trim();
                serviciuGalerie.addElement(nume, descriere, dimensiune, tip, 0, data, resolutie, locatie, tipCamera, setariCamera);
                ok = false;
            } else if (tip.equals("vid")) {
                System.out.println("Introduceti durata(secunde un nr):");
                int durata = scanner.nextInt();
                scanner.nextLine(); // consuma newline
                serviciuGalerie.addElement(nume, descriere, dimensiune, tip, durata, data, "", "", "", "");
                ok = false;
            } else {
                System.out.println("Tip invalid!");
            }
        }
    }

    // 3
    private static void stergeElement() {
        System.out.println("Introduceti numele fotografiii/videoclipului:");
        String nume = scanner.nextLine().trim();
        Element el = serviciuGalerie.viewElement(nume);
        if (el != null) {
            serviciuGalerie.removeElement(nume);
            System.out.println("Elementul sters cu succes.");
            System.out.println();
        } else {
            System.out.println("Elementul nu a fost gasit.");
            System.out.println();
        }
    }

    // 4
    private static void cautaElement() {
        System.out.println("Introduceti numele elementului cautat:");
        String numeElementVizualizare = scanner.nextLine().trim();
        Element elementVizualizat = serviciuGalerie.viewElement(numeElementVizualizare);
        if (elementVizualizat == null) {
            System.out.println("Elementul nu a fost gasit!");
        } else {
            System.out.println(elementVizualizat);
        }
    }

    private static void meniuActualizareElement() {
        System.out.println();
        System.out.println("Ce doriti sa actualizati?");
        System.out.println("=== Meniu ===");

        System.out.println("1. Fotografie");
        System.out.println("2. Videoclip");

        System.out.println("0. Iesire");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    private static void meniuFotografie() {
        System.out.println();
        System.out.println("Ce doriti sa actualizati?");
        System.out.println("=== Meniu ===");

        System.out.println("1. Nume");
        System.out.println("2. Descriere");
        System.out.println("3. Dimensiune");
        System.out.println("4. Rezolutie");
        System.out.println("5. Locatie");
        System.out.println("6. Tip camera");
        System.out.println("7. Setari camera");

        System.out.println("0. Iesire");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    public static void actualizeazaFotografie(String numeElementModificare) {
        Element newel = serviciuGalerie.viewElement(numeElementModificare);
        if (newel != null) {
            boolean exit = false;
            while (!exit) {
                meniuFotografie();
                String optiune = scanner.nextLine();
                switch (optiune) {
                    case "1" -> {
                        System.out.println("Introduceti noul nume:");
                        String numeNou = scanner.nextLine().trim();
                        newel.setName(numeNou);
                    }
                    case "2" -> {
                        System.out.println("Introduceti noua descriere:");
                        String descriereNoua = scanner.nextLine().trim();
                        newel.setDescription(descriereNoua);
                    }
                    case "3" -> {
                        System.out.println("Introduceti noua dimensiune:");
                        int dimensiuneNoua = scanner.nextInt();
                        scanner.nextLine();
                        newel.setSize(dimensiuneNoua);
                    }
                    case "4" -> {
                        System.out.println("Introduceti noua rezolutie:");
                        String rezolutieNoua = scanner.nextLine().trim();
                        ((Fotografie) newel).setResolution(rezolutieNoua);
                    }
                    case "5" -> {
                        System.out.println("Introduceti noua locatie:");
                        String locatieNoua = scanner.nextLine().trim();
                        ((Fotografie) newel).setLocation(locatieNoua);

                    }
                    case "6" -> {
                        System.out.println("Introduceti noul tip de camera:");
                        String tipCameraNoua = scanner.nextLine().trim();
                        ((Fotografie) newel).setCameraType(tipCameraNoua);
                    }
                    case "7" -> {
                        System.out.println("Introduceti noile setari ale camerei:");
                        String setariCameraNoi = scanner.nextLine().trim();
                        ((Fotografie) newel).setCameraSettings(setariCameraNoi);
                    }

                    case "0" -> exit = true;
                    default -> System.out.println("Optiune invalida.");
                }
            }

            serviciuGalerie.updateElement(numeElementModificare, newel);
        }
    }

    private static void meniuVideoclip() {
        System.out.println();
        System.out.println("Ce doriti sa actualizati?");
        System.out.println("=== Meniu ===");

        System.out.println("1. Nume");
        System.out.println("2. Descriere");
        System.out.println("3. Dimensiune");
        System.out.println("4. Durata");


        System.out.println("0. Iesire");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    public static void actualizeazaVideoclip(String numeElementModificare) {
        Element newel = serviciuGalerie.viewElement(numeElementModificare);
        if (newel != null) {
            boolean exit = false;
            while (!exit) {
                meniuVideoclip();
                String optiune = scanner.nextLine();
                switch (optiune) {
                    case "1" -> {
                        System.out.println("Introduceti noul nume:");
                        String numeNou = scanner.nextLine().trim();
                        newel.setName(numeNou);
                    }
                    case "2" -> {
                        System.out.println("Introduceti noua descriere:");
                        String descriereNoua = scanner.nextLine().trim();
                        newel.setDescription(descriereNoua);
                    }
                    case "3" -> {
                        System.out.println("Introduceti noua dimensiune:");
                        int dimensiuneNoua = scanner.nextInt();
                        scanner.nextLine();
                        newel.setSize(dimensiuneNoua);
                    }
                    case "4" -> {
                        System.out.println("Introduceti noua durata:");
                        int durataNoua = scanner.nextInt();
                        scanner.nextLine();
                        ((Videoclip) newel).setDuration(durataNoua);
                    }

                    case "0" -> exit = true;
                    default -> System.out.println("Optiune invalida.");
                }
            }

            serviciuGalerie.updateElement(numeElementModificare, newel);
        }
    }

    // 5
    private static void actualizeazaElement() {
        System.out.println("Introduceti numele elementului de modificat:");
        String numeElementModificare = scanner.nextLine().trim();

        boolean exit = false;
        while (!exit) {
            meniuActualizareElement();
            String optiune = scanner.nextLine();
            switch (optiune) {
                case "1" -> {
                    actualizeazaFotografie(numeElementModificare);
                    AuditService.logAction("actualizeazaFotografie");
                }
                case "2" -> {
                    actualizeazaVideoclip(numeElementModificare);
                    AuditService.logAction("actualizeazaVideoclip");
                }

                case "0" -> exit = true;
                default -> System.out.println("Optiune invalida.");
            }
        }

    }
    //6
    private static void filtrareElemente() {
        System.out.println("Introduceti operatia (nume/dimensiune/data/eticheta):");
        String tip = scanner.nextLine().trim();
        switch (tip) {
            case "nume" -> {
                System.out.println("Introduceti numele:");
                String nume = scanner.nextLine().trim();
                List<Element> elDupaNume = Filtru.filtrareDupaNume(serviciuGalerie.viewAllElements(), nume);
                if (!elDupaNume.isEmpty()) {
                    elDupaNume.forEach(System.out::println);
                } else {
                    System.out.println("Nu exista elemente!");
                }
            }
            case "data" -> {
                System.out.println("Introduceti data de inceput (format: dd/MM/yyyy):");
                LocalDate data = null;
                while (data == null) {
                    data = serviciuGalerie.readLocalData(scanner.nextLine());
                }
                System.out.println("Introduceti data de sfarsit (format: dd/MM/yyyy):");
                LocalDate data2 = null;
                while (data2 == null) {
                    data2 = serviciuGalerie.readLocalData(scanner.nextLine());
                }
                data = data.minusDays(1);
                data2 = data2.plusDays(1);
                List<Element> elDupaData = Filtru.filtrareDupaData(serviciuGalerie.viewAllElements(), data, data2);
                if (!elDupaData.isEmpty()) {
                    System.out.println("Fotografii dupa data:");
                    elDupaData.forEach(System.out::println);
                } else {
                    System.out.println("Nu exista elemente!");
                }
            }
            case "dimensiune" -> {
                System.out.println("Introduceti dimensiunea minima(nr Intreg):");
                int dimmin = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Introduceti dimensiunea maxima(nr intreg):");
                int dimmax = scanner.nextInt();
                scanner.nextLine();
                List<Element> elDupaDimensiune = Filtru.filtrareDupaDimensiune(serviciuGalerie.viewAllElements(), dimmin, dimmax);
                if (!elDupaDimensiune.isEmpty()) {
                    System.out.println("Fotografii dupa dimensiune:");
                    elDupaDimensiune.forEach(System.out::println);
                } else {
                    System.out.println("Nu exista elemente!");
                }
            }
            case "eticheta" -> {
                System.out.print("Introduceti eticheta dupa care doriti sa filtrati elementele: ");
                String eticheta = scanner.nextLine();

                List<Element> elDupaEticheta = Filtru.filtrareDupaEticheta(serviciuGalerie.viewAllElements(), new Eticheta(eticheta));
                if (!elDupaEticheta.isEmpty()) {
                    System.out.println("Elemente cu eticheta " + eticheta + ":");
                    elDupaEticheta.forEach(System.out::println);
                } else {
                    System.out.println("Nu exista elemente!");
                }
            }
            default -> System.out.println("Operatie invalida!");
        }
    }
    // 7
    private static void sortareElemente() {
        System.out.println("1. Sortare dupa nume");
        System.out.println("2. Sortare dupa dimensiune");
        System.out.println("3. Sortare dupa data");
        int optiuneSortare = scanner.nextInt();
        scanner.nextLine(); // consuma newline
        List<Element> elementeSortate = null;
        switch (optiuneSortare) {
            case 1 -> elementeSortate = serviciuGalerie.sortElements("name");
            case 2 -> elementeSortate = serviciuGalerie.sortElements("size");
            case 3 -> elementeSortate = serviciuGalerie.sortElements("date");
            default -> System.out.println("Optiune invalida!");
        }
        if (elementeSortate != null) {
            for (Element element : elementeSortate) {
                System.out.println(element);
                System.out.println();
            }
        }
    }
}