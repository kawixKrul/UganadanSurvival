package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.interfaces.MoveValidator;


public class CrazyAnimal extends AbstractAnimal {
    public CrazyAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        super(initialPosition, initialEnergy, genome);
    }

    @Override
    public void move(MoveValidator moveValidator) {
        super.move(moveValidator);
        this.genome.aBitOfCraziness();
    }
}
