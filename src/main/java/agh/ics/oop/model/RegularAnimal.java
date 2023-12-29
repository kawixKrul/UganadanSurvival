package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import javafx.scene.image.Image;

import java.util.List;

public class RegularAnimal extends AbstractAnimal {
    private static final String IMAGE_PATH = "src/main/resources/regularAnimal.png";

    public RegularAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        super(initialPosition, initialEnergy, genome);
    }

    @Override
    public Image getClassImage() {
        return new Image(IMAGE_PATH);
    }
}

