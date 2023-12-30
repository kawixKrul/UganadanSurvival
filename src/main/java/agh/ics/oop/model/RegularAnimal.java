package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.List;

public class RegularAnimal extends AbstractAnimal {
    private static final String IMAGE_PATH = "/animal.jpeg";
    public static final Image IMAGE = new Image(IMAGE_PATH);

    public RegularAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        super(initialPosition, initialEnergy, genome);
    }

    @Override
    public Image getClassImage() {
        return IMAGE;
    }
}

