package model;

import java.time.LocalDate;

public class Videoclip extends Element {
    private int duration;

    public Videoclip(String name, String description, int size, LocalDate creationDate, int duration) {
        super(name, description, size, creationDate);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Videoclip \n" + super.toString()+
                "\n   duration = " + duration +
                "\n";
    }
}

