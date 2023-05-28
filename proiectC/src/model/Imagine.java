package model;

import java.time.LocalDate;
import java.util.*;
public class Imagine extends Element {
    private String resolution;
    private String location;

    public Imagine(String name, String description, int size, LocalDate creationDate, String resolution, String location) {
        super(name, description, size, creationDate);
        this.resolution = resolution;
        this.location = location;
    }

    public String getResolution() {
        return resolution;
    }

    public String getLocation() {
        return location;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return  super.toString()+
                "\n   resolution = " + resolution +
                "\n   location = " + location;
    }
}
