package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.abstractions.AbstractMap;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.util.GenomePattern;

import java.util.UUID;
import java.util.stream.IntStream;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final AbstractAnimalFactory factory;
    private final int plantGrowthPerDay;
    private final int breedingEnergyRequired;
    private final int breedingEnergyConsumption;


    public Simulation(AbstractMap map,
                      AbstractAnimalFactory factory,
                      int plantGrowthPerDay,
                      int breedingEnergyRequired,
                      int breedingEnergyConsumption,
                      int startingPlantNumber,
                      int startingAnimalNumber) {
        this.map = map;
        this.factory = factory;

        this.plantGrowthPerDay = plantGrowthPerDay;
        this.breedingEnergyRequired = breedingEnergyRequired;
        this.breedingEnergyConsumption = breedingEnergyConsumption;
        for (int i = 0; i < startingAnimalNumber; ++i) {
            map.addAnimal(factory.create());
        }
        for (int i = 0; i < startingPlantNumber; ++i) {
            map.spawnGrass();
        }
    }


    @Override
    public void run() {
        while (true){
            if( map.isSimulationEnd()){
                break;
            } else {
                map.passDay(breedingEnergyRequired,plantGrowthPerDay);
            }
        }
    }
}
