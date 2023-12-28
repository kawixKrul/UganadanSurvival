package agh.ics.oop.model;

import agh.ics.oop.interfaces.AbstractFactory;

import java.util.List;

abstract public class AbstractAnimalFactory implements AbstractFactory<AbstractAnimal> {
    protected final int startEnergy;
    protected final int genomeLength;
    protected final int breedingEnergy;

    protected AbstractAnimalFactory(int startEnergy, int genomeLength, int breedingEnergy) {
        this.startEnergy = startEnergy;
        this.genomeLength = genomeLength;
        this.breedingEnergy = breedingEnergy;
    }

    abstract public AbstractAnimal create();
    abstract public AbstractAnimal create(List<Integer> genome);
}
