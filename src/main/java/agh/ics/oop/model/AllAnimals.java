package agh.ics.oop.model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;
public class AllAnimals {
    public int animalCount = 0;
    public HashMap<Vector2d, TreeSet<Animal>> animalsByPosition = new HashMap<>();
    public LinkedList<Animal> allAnimals = new LinkedList<>();
    public float averageTimeOfDeath = 0;
    public int deadAnimals = 0;

    public void addAnimal(Animal animal) {
        animalsByPosition.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>());
        animalsByPosition.get(animal.getPosition()).add(animal);
        allAnimals.add(animal);
        animalCount++;

    }

    public void removeAnimal(Animal animal) {
        animalsByPosition.get(animal.getPosition()).remove(animal);
        allAnimals.remove(animal);
        animalCount--;
    }

    public Animal strongestAnimalAtPosition(Vector2d position) {
        // wybieranie najlepszego animala na pozycji do zjedzenia trawy
        return null;
    }
    public void moveAnimals() {
        allAnimals.forEach(Animal::move);
    }
    public void breedAnimals(int energyToBreed, AbstractMap map) {
        animalsByPosition.forEach((position, set) -> {
            if (set.size() >= 2) {
                Animal first = set.last();
                Animal second = set.lower(first);
                if (second != null && second.energy >= energyToBreed) {
                    // dodaj animala
                }
            }
        });
    }
    public boolean eatGrassAtPosition(Vector2d position, int energyGain) {
        TreeSet<Animal> animals = animalsByPosition.get(position);
        if (animals != null && animals.size() > 0) {
            animals.last().eat(energyGain);
            return true;
        } else {
            return false;
        }
    }
    public float getAverageEnergy() {
        int energy = 0;
        for (Animal animal : allAnimals) {
            energy += animal.energy;
        }
        return (float) energy / allAnimals.size();
    }

    public void positionChange(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        animalsByPosition.get(oldPosition).remove(animal);
        animalsByPosition.computeIfAbsent(newPosition, k -> new TreeSet<>());
        animalsByPosition.get(newPosition).add(animal);
    }
}


