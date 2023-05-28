package model;

import java.util.Objects;

public class Eticheta {
    private String name;

    public Eticheta(String nume) {
        this.name = nume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eticheta eticheta = (Eticheta) o;
        return Objects.equals(name, eticheta.name);
    }


    @Override
    public String toString() {
        return "nume = " + name;
    }
}
