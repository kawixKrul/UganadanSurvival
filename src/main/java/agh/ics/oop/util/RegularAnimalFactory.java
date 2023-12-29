package agh.ics.oop.util;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.model.*;


public class RegularAnimalFactory extends AbstractAnimalFactory {
    public RegularAnimalFactory(int startEnergy, GenomePattern genomePattern, int breedingEnergy, Boundary boundary) {
        super(startEnergy, genomePattern, breedingEnergy, boundary);
    }
    @Override
    public AbstractAnimal create() {
        return new RegularAnimal(Vector2d.getRandomVector2d(this.boundary), this.startEnergy, this.genomePattern.create());
    }

    @Override
    public AbstractAnimal create(Vector2d parentPosition) {
        return new RegularAnimal(parentPosition, this.breedingEnergy*2, this.genomePattern.create());
    }
}
