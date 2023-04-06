package model;

public class Eticheta {
    private String nume;

    public Eticheta(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "nume='" + nume ;
    }
}
