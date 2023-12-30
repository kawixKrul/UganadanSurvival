package agh.ics.oop.model;


import agh.ics.oop.abstractions.AbstractPlant;
import javafx.scene.image.Image;

import java.net.URL;

public class Grass extends AbstractPlant {
    public static final String IMAGE_PATH = "/plant.jpeg";
    public static final Image IMAGE = new Image(IMAGE_PATH);

    public Grass(Vector2d position, int energy) {
        super(position, energy);
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Image getClassImage() {
        return IMAGE;
    }
}