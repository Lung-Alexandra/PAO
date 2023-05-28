package menu;

import java.util.List;
import java.util.Scanner;

import model.Album;
import service.*;

public class MeniuAlbume implements Meniu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ServiciuGalerie serviciuGalerie = new ServiciuGalerie();
    private static boolean exit1 = false;

    public static void setExit1(boolean exit1) {
        MeniuAlbume.exit1 = exit1;
    }

    public static void afisareMeniuAlbume() {
        System.out.println();
        System.out.println("=== Meniu Albume ===");
        System.out.println("1. Afiseaza toate albumele");
        System.out.println("2. Creare album gol");
        System.out.println("3. Actualizare nume album");
        System.out.println("4. Stergere album");
        System.out.println("5. Adaugare elemente in album");
        System.out.println("6. Stergere element din album");
        System.out.println("7. Vizualizare elemente album");
        System.out.println("0. Inapoi");


        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    public static void gestioneazaOptiuneAlbume(String optiune) {

        switch (optiune) {
            case "1" -> {
                afiseazaAlbume();
                AuditService.logAction("afiseazaAlbume");
            }
            case "2" -> {
                adaugaAlbum();
                AuditService.logAction("adaugaAlbum");
            }
            case "3" -> {
                actulizeazaAlbum();
                AuditService.logAction("actulizeazaAlbum");
            }
            case "4" -> {
                stergeAlbum();
                AuditService.logAction("stergeAlbum");
            }
            case "5" -> {
                adaugaElementAlbum();
                AuditService.logAction("adaugaElementAlbum");
            }
            case "6" -> {
                stergeElementAlbum();
                AuditService.logAction("stergeElementAlbum");
            }
            case "7" -> {
                vizualizareElemAlbum();
                AuditService.logAction("vizualizareElemAlbum");
            }
            case "0" -> exit1 = true; // Ieșire din metoda curentă pentru a reveni la meniul principal

            default -> System.out.println("Optiune invalida.");
        }

    }

    @Override
    public void run() {

        while (!exit1) {
            afisareMeniuAlbume();
            String optiune = scanner.nextLine();
            gestioneazaOptiuneAlbume(optiune);
        }

    }
    //1
    private static void afiseazaAlbume() {
        List<Album> albume = serviciuGalerie.viewAllAlbums();
        if (!albume.isEmpty()) {
            albume.forEach(System.out::println);
        } else {
            System.out.println("Nu exista albume in galerie!");
        }
        System.out.println();
    }
    //2
    private static void adaugaAlbum() {
        System.out.println("Introduceti numele albumului:");
        String numeAlbum = scanner.nextLine();
        serviciuGalerie.createAlbum(numeAlbum);

    }

    //3
    private static void actulizeazaAlbum() {
        System.out.println("Introduceti numele albumului:");
        String numeAlbum = scanner.nextLine();

        System.out.println("Introduceti noul nume:");
        String numeNou = scanner.nextLine().trim();

        serviciuGalerie.updateAlbum(numeAlbum, numeNou);
    }

    // 5
    private static void adaugaElementAlbum() {
        System.out.println("Introduceti numele elementului de adaugat in album:");
        String numeElementAdaugare = scanner.nextLine();
        System.out.println("Introduceti numele albumului:");
        String numeAl = scanner.nextLine();
        serviciuGalerie.addElementToAlbum(numeAl, numeElementAdaugare);
    }

    //6
    public static void stergeElementAlbum() {
        System.out.println("Introduceti numele elementului de sters din album:");
        String numeElementStergereAlbum = scanner.nextLine();
        System.out.println("Introduceti numele albumului:");
        String numeAlbumStergere = scanner.nextLine();
        serviciuGalerie.removeElementFromAlbum(numeAlbumStergere, numeElementStergereAlbum);
    }

    // 4
    private static void stergeAlbum() {
        System.out.println("Introduceti numele albumului:");
        String nume = scanner.nextLine();
        serviciuGalerie.deleteAlbum(nume);
    }

    // 7
    public static void vizualizareElemAlbum() {
        System.out.println("Introduceti numele albumului:");
        String nume = scanner.nextLine();
        serviciuGalerie.viewAlbumContent(nume);
    }




}