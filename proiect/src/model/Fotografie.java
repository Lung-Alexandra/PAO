package model;

import java.time.LocalDate;

public class Fotografie extends Imagine {
    private String cameraType;
    private String cameraSettings;

    public Fotografie(String name, String description, int size, LocalDate creationDate, String resolution, String location,
                      String cameraType, String cameraSettings) {
        super(name, description, size, creationDate, resolution, location);
        this.cameraType = cameraType;
        this.cameraSettings = cameraSettings;
    }


    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public void setCameraSettings(String cameraSettings) {
        this.cameraSettings = cameraSettings;
    }

    public String getCameraType() {
        return cameraType;
    }

    public String getCameraSettings() {
        return cameraSettings;
    }



    @Override
    public String toString() {
        return "Fotografie \n" + super.toString()+
                "\n   cameraType = " + cameraType +
                "\n   cameraSettings = " + cameraSettings +
                "\n";
    }
}
