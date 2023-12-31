package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.util.CSVFileWriter;

import java.io.IOException;


public class Simulation implements Runnable {
    private final WorldMap map;
    private final int plantGrowthPerDay;
    private int day = 0;


    public Simulation(AbstractWorldMap map, int startingPlantCount, int startingAnimalCount, int plantGrowthPerDay) {
        this.map = map;
        this.plantGrowthPerDay = plantGrowthPerDay;
        map.spawnAnimals(startingAnimalCount);
        map.spawnPlants(startingPlantCount);
    }


    @Override
    public void run() {
        while (true) {
            map.removeDeadAnimals(day);
            if (map.isSimulationEnd()) {
                shutdown();
                break;
            }
            map.getElements()
                    .stream()
                    .filter(e -> e instanceof AbstractAnimal)
                    .forEach(a -> {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        map.move((AbstractAnimal) a);
                    });
            map.consumePlants();
            //map.procreateAllAnimals();
            map.spawnPlants(plantGrowthPerDay);
            day++;
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
                        e.printStackTrace();
                        System.out.println("Failed to close file");
                    }});
        System.out.println("Simulation ended after " + day + " days");
    }
}
