package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.interfaces.MoveValidator;
import javafx.scene.image.Image;

public class CrazyAnimal extends AbstractAnimal {
    public static final String IMAGE_PATH = "/crazyAnimal.jpg";
    public static final Image IMAGE = new Image(IMAGE_PATH);
    public CrazyAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        super(initialPosition, initialEnergy, genome);
    }

    @Override
    public void move(MoveValidator moveValidator) {
        super.move(moveValidator);
        this.genome.aBitOfCraziness();
    }

    @Override
    public Image getClassImage() {
        return IMAGE;
    }
}
