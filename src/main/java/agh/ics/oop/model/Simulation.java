package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.util.CSVFileWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantGrowthPerDay;
    private final List<AbstractAnimal> activeAnimals = new LinkedList<>();
    private int day = 0;


    public Simulation(AbstractWorldMap map, int startingPlantCount, int startingAnimalCount, int plantGrowthPerDay) {
        this.map = map;
        this.plantGrowthPerDay = plantGrowthPerDay;
        map.spawnAnimals(startingAnimalCount);
        map.spawnPlants(startingPlantCount);
        activeAnimals.addAll(map.getAnimals());
    }


    @Override
    public void run() {
        while (true) {
            activeAnimals.removeAll(map.removeDeadAnimals(day));
            if (activeAnimals.isEmpty()) {
                shutdown();
                break;
            }
            for (AbstractAnimal animal : activeAnimals) {
                animal.move(map);
            }
            map.consumePlants();
            activeAnimals.addAll(map.procreateAllAnimals());
            map.spawnPlants(plantGrowthPerDay);
            activeAnimals.forEach(AbstractAnimal::incrementAge);
            day++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Day " + day);
        }
    }

    public void shutdown() {
        map.getObservers()
                .stream()
                .filter(o -> o instanceof CSVFileWriter)
                .forEach(o -> {
                    try {
                        ((CSVFileWriter) o).closeFile();}
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Failed to close file");
                    }});
        System.out.println("Simulation ended after " + day + " days");
    }
}
