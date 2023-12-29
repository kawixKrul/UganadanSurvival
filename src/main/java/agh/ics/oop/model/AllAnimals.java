package agh.ics.oop.model;


import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractMap;
import agh.ics.oop.abstractions.AbstractPlant;
import agh.ics.oop.interfaces.MoveValidator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
public class AllAnimals {
    public int animalCount = 0;
    public HashMap<Vector2d, TreeSet<AbstractAnimal>> animalsByPosition = new HashMap<>();
    public LinkedList<AbstractAnimal> allAnimals = new LinkedList<>();
    public float averageTimeOfDeath = 0;
    public int deadAnimals = 0;
    private MoveValidator moveValidator;
    public void addAnimal(AbstractAnimal animal) {
        animalsByPosition.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>());
        animalsByPosition.get(animal.getPosition()).add(animal);
        allAnimals.add(animal);
        animalCount++;

    }

    public void removeAnimal(AbstractAnimal animal) {
        animalsByPosition.get(animal.getPosition()).remove(animal);
        allAnimals.remove(animal);
        animalCount--;
    }

    public AbstractAnimal strongestAnimalAtPosition(Vector2d position) {
        // wybieranie najlepszego animala na pozycji do zjedzenia trawy
        if (animalsByPosition.get(position) != null && !animalsByPosition.get(position).isEmpty()) {
            return animalsByPosition.get(position).last();
        } else return null;

    }
    public void moveAnimals() {
        allAnimals.forEach(a -> a.move(moveValidator));
    }
    public void breedAnimals(int energyToBreed, AbstractMap map) {
        animalsByPosition.forEach((position, set) -> {
            if (set.size() >= 2) {
                AbstractAnimal first = set.last();
                AbstractAnimal second = set.lower(first);
                if (second != null && second.getEnergy() >= energyToBreed) {
                    // dodaj animala
                    //map.addAnimal(first.procreate(second));
                }
            }
        });
    }
    public void checkIfAllAnimalsAlive(int day){
        Iterator<AbstractAnimal> iter = allAnimals.iterator();
        while (iter.hasNext()){
            AbstractAnimal animal = iter.next();
            if (animal.checkIfAlive(day)){
                iter.remove();
                averageTimeOfDeath = (averageTimeOfDeath * deadAnimals + animal.getAge()) / (deadAnimals + 1);
                deadAnimals++;
            }
        }
    }
    public boolean eatGrassAtPosition(Vector2d position, AbstractPlant energyGain) {
        TreeSet<AbstractAnimal> animals = animalsByPosition.get(position);
        if (animals != null && !animals.isEmpty()) {
            animals.last().eatGrass(energyGain);
            return true;
        } else {
            return false;
        }
    }
    public float getAverageEnergy() {
        int energy = 0;
        for (AbstractAnimal animal : allAnimals) {
            energy += animal.getEnergy();
        }
        return (float) energy / allAnimals.size();
    }

    public void positionChange(AbstractAnimal animal, Vector2d oldPosition, Vector2d newPosition) {
        animalsByPosition.get(oldPosition).remove(animal);
        animalsByPosition.computeIfAbsent(newPosition, k -> new TreeSet<>());
        animalsByPosition.get(newPosition).add(animal);
    }
}


