package agh.ics.oop.model;

import agh.ics.oop.interfaces.MoveValidator;

import java.util.List;
import java.util.Random;

public class CrazyAnimal extends AbstractAnimal {
    public CrazyAnimal(Vector2d initialPosition, int initialEnergy, int genomeLength) {
        super(initialPosition, initialEnergy, genomeLength);
    }

    public CrazyAnimal(Vector2d initialPosition, int initialEnergy, List<Integer> genome) {
        super(initialPosition, initialEnergy, genome);
    }

    /**
     * modification of move method from its super class
     * small chance to reset and change active gene that will be activated in the next turn after the movement
     * @param moveValidator
     */
    @Override
    public void move(MoveValidator moveValidator) {
        super.move(moveValidator);
        if (Math.random() < 0.2) {
            this.geneActive = new Random().nextInt() % getGenome().size();
        }
    }
}
