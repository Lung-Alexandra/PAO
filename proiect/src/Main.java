import model.Album;
import model.Element;
import model.Eticheta;
import service.AuditService;
import service.ServiciuGalerie;
import util.Filtru;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Date predefinite pentru testare
//    static Map<String, Element> elements = new HashMap<>();
//    static Map<String, Eticheta> tags = new HashMap<>();
//    static List<Album> albums = new ArrayList<>();
    private static final ServiciuGalerie serviciuGalerie = new ServiciuGalerie();

//
//    public static void init() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        Fotografie foto = new Fotografie("img1", "desc", 200, LocalDate.parse("12/12/2000", formatter), "1900*240", null, "tipcam", "set");
//        Fotografie foto1 = new Fotografie("img2", "desc1", 500, LocalDate.parse("12/11/2000", formatter), "1900*240", null, "tipcam", "set");
//        Fotografie foto2 = new Fotografie("img3", "desc2", 100, LocalDate.parse("20/11/2000", formatter), "1900*240", null, "tipcam", "set");
//        Fotografie foto3 = new Fotografie("img4", "desc3", 700, LocalDate.parse("02/12/2000", formatter), "1900*240", null, "tipcam", "set");
//        Videoclip vid1 = new Videoclip("vid1", "desc3", 500, LocalDate.parse("05/12/2000", formatter), 1900);
//        Videoclip vid2 = new Videoclip("vid2", "desc3", 400, LocalDate.parse("03/12/2000", formatter), 1800);
//
//        Album album1 = new Album("Album1");
//        Album album2 = new Album("Album2");
//        elements.put(foto.getName(), foto);
//        elements.put(foto1.getName(), foto1);
//        elements.put(foto2.getName(), foto2);
//        elements.put(foto3.getName(), foto3);
//        elements.put(vid1.getName(), vid1);
//        elements.put(vid2.getName(), vid2);
//
//        albums.add(album1);
//        albums.add(album2);
//
//        album1.addElementToAlbum(foto1);
//        album1.addElementToAlbum(foto);
//        album1.addElementToAlbum(foto2);
//        album2.addElementToAlbum(foto3);
//        album2.addElementToAlbum(vid1);
//        album2.addElementToAlbum(vid2);
//    }

    public static void main(String[] args) {
        //for testing
//        init();

        boolean exit = false;
        while (!exit) {
            afisareMeniu();
            String optiune = scanner.nextLine();
            switch (optiune) {
                case "1" -> {afiseazaElemente(); AuditService.logAction("afiseazaElemente");}
                case "2" -> {adaugaElement();AuditService.logAction("adaugaElement");}
                case "3" -> {stergeElement();AuditService.logAction("stergeElement");}
                case "4" -> {cautaElement();AuditService.logAction("cautaElement");}
                case "5" -> {actualizeaza();AuditService.logAction("actualizeaza");}
                case "6" -> {adaugaEticheta();AuditService.logAction("adaugaEticheta");}
                case "7" -> {vizualizareDupaEticheta();AuditService.logAction("vizualizareDupaEticheta");}
                case "8" -> {sortareElemente();AuditService.logAction("sortareElemente");}
                case "9" -> {adaugaAlbum();AuditService.logAction("adaugaAlbum");}
                case "10" -> {adaugaElementAlbum();AuditService.logAction("adaugaElementAlbum");}
                case "11" -> {stergeElementAlbum();AuditService.logAction("stergeElementAlbum");}
                case "12" -> {stergeAlbum();AuditService.logAction("stergeAlbum");}
                case "13" -> {vizualizareElemAlbum();AuditService.logAction("vizualizareElemAlbum");}
                case "14" -> {afiseazaAlbume();AuditService.logAction("afiseazaAlbume");}
                case "15" -> {filtrareElemente();AuditService.logAction("filtrareElemente");}
                case "16" -> {dimensiuneTotala();AuditService.logAction("dimensiuneTotala");}
                case "0" -> {
                    scanner.close();
                    exit = true;
                }
                default -> System.out.println("Optiune invalida.");
            }
        }
    }



    private static void afisareMeniu() {
        System.out.println();
        System.out.println("Bun venit in galerie! Ce doriti sa faceti?");
        System.out.println("=== Meniu ===");

        System.out.println("1. Vizualizarea tuturor imaginilor/videoclipurilor din galerie");
        System.out.println("2. Adaugare imagine/videoclip in galerie");
        System.out.println("3. Stergere imagine/videoclip din galerie");
        System.out.println("4. Cautare imagine/videoclip in galerie dupa nume");
        System.out.println("5. Actualizare (nume si descriere) imagine/videoclip din galerie");
        System.out.println("6. Adaugare/Stergere eticheta la imagine/videoclip");
        System.out.println("7. Vizualizare elemente dupa eticheta");
        System.out.println("8. Sortare imagini/videoclipuri dupa diferite criterii");
        System.out.println("9. Creare album gol");
        System.out.println("10. Adaugare elemente in album");
        System.out.println("11. Stergere element din album");
        System.out.println("12. Stergere album");
        System.out.println("13. Vizualizare elemente album");
        System.out.println("14. Vizualizare toate albumele");
        System.out.println("15. Filtrare imagini/videoclipuri dupa diferite criterii");

        System.out.println("0. Iesire");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
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
        System.out.println("Introduceti numele imaginii/videoclipului:");
        String nume = scanner.nextLine().trim();
        Element el = serviciuGalerie.viewElement(nume);
        if (el != null) {
            serviciuGalerie.removeElement(nume);
            System.out.println("Elementul stears cu succes.");
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

    // 5
    private static void actualizeaza() {
        System.out.println("Introduceti numele elementului de modificat:");
        String numeElementModificare = scanner.nextLine().trim();
        System.out.println("Introduceti noul nume:");
        String numeNou = scanner.nextLine().trim();
        System.out.println("Introduceti noua descriere:");
        String descriereNoua = scanner.nextLine().trim();
        serviciuGalerie.updateElement(numeElementModificare, numeNou, descriereNoua);
    }

    // 6
    private static void adaugaEticheta() {
        System.out.println("Introduceti operatia (adaugare/stergere):");
        String tip = scanner.nextLine().trim();
        if (tip.equals("adaugare")) {
            System.out.println("Introduceti numele elementului:");
            String numeElementTag = scanner.nextLine();
            System.out.println("Introduceti numele etichetei:");
            String tag = scanner.nextLine();
            serviciuGalerie.addTag(numeElementTag, tag);

        } else if (tip.equals("stergere")) {
            System.out.println("Introduceti numele elementului:");
            String numeElementTag = scanner.nextLine();
            System.out.println("Introduceti numele etichetei:");
            String nume = scanner.nextLine();
            serviciuGalerie.removeTag(numeElementTag, nume);
        } else {
            System.out.println("Operatie invalida!");
        }
    }

    // 7
    private static void vizualizareDupaEticheta() {
        System.out.println("Introduceti eticheta-ul:");
        String tagCautare = scanner.nextLine();
        serviciuGalerie.viewElementsByTag(tagCautare);
    }

    // 8
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

    //9
    private static void adaugaAlbum() {
        System.out.println("Introduceti numele albumului:");
        String numeAlbum = scanner.nextLine();
        serviciuGalerie.createAlbum(numeAlbum);

    }

    // 10
    private static void adaugaElementAlbum() {
        System.out.println("Introduceti numele elementului de adaugaElementt in album:");
        String numeElementAdaugare = scanner.nextLine();
        System.out.println("Introduceti numele albumului:");
        String numeAl = scanner.nextLine();
        serviciuGalerie.addElementToAlbum(numeAl, numeElementAdaugare);
    }

    //11
    public static void stergeElementAlbum() {
        System.out.println("Introduceti numele elementului de sters din album:");
        String numeElementStergereAlbum = scanner.nextLine();
        System.out.println("Introduceti numele albumului:");
        String numeAlbumStergere = scanner.nextLine();
        serviciuGalerie.removeElementFromAlbum(numeAlbumStergere, numeElementStergereAlbum);
    }

    // 12
    private static void stergeAlbum() {
        System.out.println("Introduceti numele albumului:");
        String nume = scanner.nextLine();
        serviciuGalerie.deleteAlbum(nume);
    }

    // 13
    public static void vizualizareElemAlbum() {
        System.out.println("Introduceti numele albumului:");
        String nume = scanner.nextLine();
        serviciuGalerie.viewAlbumContent(nume);
    }

    //14
    private static void afiseazaAlbume() {
        List<Album> albume = serviciuGalerie.viewAllAlbums();
        if (!albume.isEmpty()) {
            albume.forEach(System.out::println);
        } else {
            System.out.println("Nu exista albume in galerie!");
        }
        System.out.println();
    }

    //15
    private static void filtrareElemente() {
        System.out.println("Introduceti operatia (nume/dimensiune/data/eticheta):");
        String tip = scanner.nextLine().trim();
        switch (tip) {
            case "nume" -> {
                System.out.println("Introduceti numle:");
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
                LocalDate data2 = null;
                while (data2 == null) {
                    data2 = serviciuGalerie.readLocalData(scanner.nextLine());
                }
                List<Element> elDupaData = Filtru.filtrareDupaData(serviciuGalerie.viewAllElements(), data, data2);
                if (!elDupaData.isEmpty()) {
                    System.out.println("Imagini dupa data:");
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
                    System.out.println("Imagini dupa dimensiune:");
                    elDupaDimensiune.forEach(System.out::println);
                } else {
                    System.out.println("Nu exista elemente!");
                }
            }
            case "eticheta"->{
                System.out.print("Introduceti eticheta dupa care doriti sa filtrati elementele: ");
                String eticheta = scanner.nextLine();

                List<Element> elDupaEticheta = Filtru.filtrareDupaEticheta(serviciuGalerie.viewAllElements(),new Eticheta(eticheta));
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

    //16
    private static void dimensiuneTotala() {
        int totalSize = serviciuGalerie.sizeOfGalery();

        System.out.println("Dimensiunea totală a elementelor este: " + totalSize);
    }

}
