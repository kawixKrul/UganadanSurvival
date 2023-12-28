package agh.ics.oop.model;

import agh.ics.oop.interfaces.WorldMap;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final int startEnergy;
    private final int plantEnergy;
    private final int jungleRatio;
    private final int startAnimals;
    private final int startPlants;
    private final int plantGrowthPerDay;
    private final int breedingReadyEnergy;
    private final int breedingConsumptionEnergy;
    private final int minimumMutationNumber;
    private final int maximumMutationNumber;
    private final int genomeLength;
    private final boolean crazyAnimalEnabled;
    private final boolean toxicPlantsEnabled;
    private final boolean saveToFileEnabled;

    // TODO FIX SIMULATION CONSTRUCTOR
    public Simulation(int mapWidth,
                      int mapHeight,
                      int startEnergy,
                      int plantEnergy,
                      int jungleRatio,
                      int startAnimals,
                      int startPlants,
                      int plantGrowthPerDay,
                      int breedingReadyEnergy,
                      int breedingConsumptionEnergy,
                      int minimumMutationNumber,
                      int maximumMutationNumber,
                      int genomeLength,
                      boolean crazyAnimalEnabled,
                      boolean toxicPlantsEnabled,
                      boolean saveToFileEnabled) {
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.startAnimals = startAnimals;
        this.startPlants = startPlants;
        this.plantGrowthPerDay = plantGrowthPerDay;
        this.breedingReadyEnergy = breedingReadyEnergy;
        this.breedingConsumptionEnergy = breedingConsumptionEnergy;
        this.minimumMutationNumber = minimumMutationNumber;
        this.maximumMutationNumber = maximumMutationNumber;
        this.genomeLength = genomeLength;
        this.crazyAnimalEnabled = crazyAnimalEnabled;
        this.toxicPlantsEnabled = toxicPlantsEnabled;
        this.saveToFileEnabled = saveToFileEnabled;
        this.map = new WorldMap(mapHeight, mapWidth);
    }


    @Override
    public void run() {
        // TODO IMPLEMENT SIMULATION
    }
}
