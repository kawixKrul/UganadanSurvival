package agh.ics.oop.model;

import java.util.List;

public class RegularAnimal extends AbstractAnimal {

    public RegularAnimal(Vector2d initialPosition, int initialEnergy, int genomeLength) {
        super(initialPosition, initialEnergy, genomeLength);
    }

    public RegularAnimal(Vector2d initialPosition, int initialEnergy, List<Integer> genome) {
        super(initialPosition, initialEnergy, genome);
    }


}

