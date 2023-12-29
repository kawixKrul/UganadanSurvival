package agh.ics.oop.model;


import agh.ics.oop.abstractions.AbstractPlant;
import javafx.scene.image.Image;

public class Grass extends AbstractPlant {
    public static final String IMAGE_PATH = "src/main/resources/grass.png";

    public Grass(Vector2d position, int energy) {
        super(position, energy);
    }


    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Image getClassImage() {
        return new Image(IMAGE_PATH);
    }
}