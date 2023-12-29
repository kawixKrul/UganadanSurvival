package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.abstractions.AbstractMap;
import agh.ics.oop.interfaces.WorldMap;

import java.util.UUID;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final AbstractAnimalFactory factory;
    private final UUID uuid;
    private final GenomePattern genomePattern;
    private final int plantGrowthPerDay;
    private final int breedingEnergyRequired;
    private final int breedingEnergyConsumption;


    public Simulation(AbstractMap map,
                      AbstractAnimalFactory factory,
                      GenomePattern genomePattern,
                      int plantGrowthPerDay,
                      int breedingEnergyRequired,
                      int breedingEnergyConsumption,
                      int startingPlantNumber,
                      int startingAnimalNumber) {
        this.map = map;
        this.factory = factory;
        this.uuid = new UUID(System.currentTimeMillis(), System.currentTimeMillis());
        this.genomePattern = genomePattern;
        this.plantGrowthPerDay = plantGrowthPerDay;
        this.breedingEnergyRequired = breedingEnergyRequired;
        this.breedingEnergyConsumption = breedingEnergyConsumption;
    }


    @Override
    public void run() {
        // TODO IMPLEMENT SIMULATION
    }

    public WorldMap getMap() {
        return this.map;
    }
}
