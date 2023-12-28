package agh.ics.oop.util;

import agh.ics.oop.model.AbstractAnimal;
import agh.ics.oop.model.AbstractAnimalFactory;
import agh.ics.oop.model.RegularAnimal;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class RegularAnimalFactory extends AbstractAnimalFactory {
    public RegularAnimalFactory(int startEnergy, int genomeLength, int breedingEnergy) {
        super(startEnergy, genomeLength, breedingEnergy);
    }
    @Override
    public AbstractAnimal create() {
        return new RegularAnimal(new Vector2d(0, 0), this.startEnergy, this.genomeLength);
    }

    @Override
    public AbstractAnimal create(List<Integer> genome) {
        return new RegularAnimal(new Vector2d(0, 0), this.startEnergy, genome);
    }
}
