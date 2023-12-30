package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractPlant;
import javafx.scene.image.Image;

import java.awt.*;

public class ToxicPlant extends AbstractPlant {
    public static final String IMAGE_PATH = "src/main/resources/toxic_plant.png";

    public ToxicPlant(Vector2d position, int energy) {
        super(position, (-1)*energy);
    }


    @Override
    public String toString() {
        return "!";
    }

    @Override
    public Image getClassImage() {
        return new Image(IMAGE_PATH);
    }
}
