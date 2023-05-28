
package menu;

import java.util.Scanner;

import service.AuditService;
import service.ServiciuGalerie;

public class MeniuService implements Meniu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ServiciuGalerie serviciuGalerie = new ServiciuGalerie();

    public static void afisareMeniuPrincipal() {
        System.out.println();
        System.out.println("Bun venit in galerie! Ce doriti sa faceti?");
        System.out.println("=== Meniu Principal ===");
        System.out.println("1. Meniu fotografii/videocipuri");
        System.out.println("2. Meniu Etichete");
        System.out.println("3. Meniu Albume");
        System.out.println("4. Afisare dimensiune galerie");
        System.out.println("0. Iesire");
        System.out.print("Alegerea dumneavoastra: ");
        System.out.println();
    }

    public static void gestioneazaOptiunePrincipal(String optiune) {
        switch (optiune) {
            case "1" -> {
                MeniuElemente meniuElemente = new MeniuElemente();
                meniuElemente.run();
            }
            case "2" -> {
                MeniuEtichete meniuEtichete = new MeniuEtichete();
                meniuEtichete.run();
            }
            case "3" -> {
                MeniuAlbume meniuAlbume = new MeniuAlbume();
                meniuAlbume.run();
            }
            case "4" -> {
                dimensiuneTotala();
                AuditService.logAction("dimensiuneTotala");
            }
            case "0" -> System.exit(0);
            default -> System.out.println("Optiune invalida.");
        }
    }


    @Override
    public  void run() {
        boolean exit = false;
        while (!exit) {
            MeniuElemente.setExit1(false);
            MeniuEtichete.setExit1(false);
            MeniuAlbume.setExit1(false);
            afisareMeniuPrincipal();
            String optiune = scanner.nextLine();
            gestioneazaOptiunePrincipal(optiune);
        }
        scanner.close();
    }

    //4
    private static void dimensiuneTotala() {
        int totalSize = serviciuGalerie.sizeOfGalery();

        System.out.println("Dimensiunea totală a elementelor este: " + totalSize);
    }


}
