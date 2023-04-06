package model;

import java.util.*;

public class Videoclip extends Element {
    private int duration;

    public Videoclip(String name, String description, int size, Date creationDate, int duration) {
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
        return "Videoclip {\n" + super.toString()+
                "\nduration=" + duration +
                "\n}";
    }
}

