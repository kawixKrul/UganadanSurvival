package agh.ics.oop.util;

import agh.ics.oop.model.AbstractAnimal;
import agh.ics.oop.model.AbstractAnimalFactory;
import agh.ics.oop.model.CrazyAnimal;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class CrazyAnimalFactory extends AbstractAnimalFactory {
    protected CrazyAnimalFactory(int startEnergy, int genomeLength, int breedingEnergy) {
        super(startEnergy, genomeLength, breedingEnergy);
    }

    @Override
    public AbstractAnimal create() {
        return new CrazyAnimal(new Vector2d(0, 0), this.startEnergy, this.genomeLength);
    }

    @Override
    public AbstractAnimal create(List<Integer> genome) {
        return new CrazyAnimal(new Vector2d(0, 0), this.startEnergy, genome);
    }
}
