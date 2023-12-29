package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;

import java.util.List;

public class RegularAnimal extends AbstractAnimal {

    public RegularAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        super(initialPosition, initialEnergy, genome);
    }
}

