package model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eticheta eticheta = (Eticheta) o;
        return Objects.equals(nume, eticheta.nume);
    }


    @Override
    public String toString() {
        return "nume=" + nume ;
    }
}
