package menu;

import java.util.Scanner;

import service.*;

public class MeniuEtichete implements Meniu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ServiciuGalerie serviciuGalerie = new ServiciuGalerie();
    private static boolean exit1 = false;
    public static void setExit1(boolean exit1) {
        MeniuEtichete.exit1 = exit1;
    }

    public static void afisareMeniuEtichete() {
        System.out.println();
        System.out.println("=== Meniu Etichete ===");
        System.out.println("1. Adauga eticheta la fotografie/videoclip");
        System.out.println("2. Sterge eticheta la fotografie/videoclip");
        System.out.println("3. Vizualizare elemente dupa eticheta");
        System.out.println("0. Inapoi");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    public static void gestioneazaOptiuneEtichete(String optiune) {

        switch (optiune) {
            case "1" -> {
                adaugaEticheta();
                AuditService.logAction("adaugaEticheta");
            }
            case "2" -> {
                stergeEticheta();
                AuditService.logAction("stergeEticheta");
            }
            case "3" -> {
                vizualizareDupaEticheta();
                AuditService.logAction("vizualizareDupaEticheta");
            }
            case "0" -> exit1 = true; // Ieșire din metoda curentă pentru a reveni la meniul principal

            default -> System.out.println("Optiune invalida.");
        }

    }

    @Override
    public void run() {

        while (!exit1) {
            afisareMeniuEtichete();
            String optiune = scanner.nextLine();
            gestioneazaOptiuneEtichete(optiune);
        }

    }

    // 1
    private static void adaugaEticheta() {
        System.out.println("Introduceti numele elementului:");
        String numeElementTag = scanner.nextLine();
        System.out.println("Introduceti numele etichetei:");
        String tag = scanner.nextLine();
        serviciuGalerie.addTag(numeElementTag, tag);
    }


    //2
    private static void stergeEticheta() {
        System.out.println("Introduceti numele elementului:");
        String numeElementTag = scanner.nextLine();
        System.out.println("Introduceti numele etichetei:");
        String nume = scanner.nextLine();
        serviciuGalerie.removeTag(numeElementTag, nume);
    }

    // 3
    private static void vizualizareDupaEticheta() {
        System.out.println("Introduceti eticheta-ul:");
        String tagCautare = scanner.nextLine();
        serviciuGalerie.viewElementsByTag(tagCautare);
    }
}