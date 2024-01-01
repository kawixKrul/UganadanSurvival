package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.interfaces.ChangeListener;
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
    private final List<CSVFileWriter> observers = new LinkedList<>();


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
            killTime();
            if (activeAnimals.isEmpty()) {
                shutdown();
                break;
            }
            for (AbstractAnimal animal : activeAnimals) {
                animal.move(map);
            }
            killTime();
            map.consumePlants();
            killTime();
            activeAnimals.addAll(map.procreateAllAnimals());
            killTime();
            map.spawnPlants(plantGrowthPerDay);
            killTime();
            activeAnimals.forEach(AbstractAnimal::incrementAge);
            day++;
            System.out.println("Day " + day);
            simulationChanged(this.toString());
        }
    }

    public void shutdown() {
        this.observers.forEach(o -> {
            try {
                o.closeFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
        System.out.println("Simulation ended on day " + day);
    }

    private void simulationChanged(String message) {
        for (CSVFileWriter observer : observers) {
            observer.objectChanged(this, message);
        }
    }

    public void addObserver(CSVFileWriter observer) {
        this.observers.add(observer);
    }

    public void killTime() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.join("\n", activeAnimals.stream().map(a -> day+","+a.toString()).toList())+"\n";
    }
}
