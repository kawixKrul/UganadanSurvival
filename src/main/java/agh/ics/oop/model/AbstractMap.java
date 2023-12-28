package agh.ics.oop.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractMap {
    public int width;
    public int height;
    public HashMap<Vector2d, AbstractPlant> grassSet = new HashMap<>();

    public AllAnimals animals = new AllAnimals();

    public int currentday = 0;


    private void spawnGrass() {
        boolean grassSpawned;
        if (Math.random() < 0.8) {
            grassSpawned = spawnGrassPreferred();
            if (!grassSpawned) spawnGrassNonPreferred();
        } else {
            grassSpawned = spawnGrassNonPreferred();
            if (!grassSpawned) spawnGrassPreferred();
        }
    }

    public void addAnimal(AbstractAnimal animal){
        animals.addAnimal(animal);
    }

    public void removeAnimal(AbstractAnimal animal){
        animals.removeAnimal(animal);
    }
    public void whichAreDead(){
        animals.checkIfAllAnimalsAlive(currentday);
    }

    public void moveAnimals(){
        animals.moveAnimals();
    }

    public void eatGrass() {
        Iterator<Map.Entry<Vector2d,AbstractPlant>> it = grassSet.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry<Vector2d,AbstractPlant> next = it.next();
            if (animals.eatGrassAtPosition(next.getKey(), next.getValue()))
                it.remove();
        }
    }

    public void breedAnimals(int energyToBreed) {
        animals.breedAnimals(energyToBreed, this);
    }

    public AbstractAnimal animalAt(Vector2d position) {
        return animals.strongestAnimalAtPosition(position);
    }

    public boolean grassAt(Vector2d position) {
        return grassSet.containsKey(position);
    }

    public void deathOfAnimal(AbstractAnimal animal) {
        this.removeAnimal(animal);
    }
    protected abstract boolean spawnGrassPreferred();

    protected abstract boolean spawnGrassNonPreferred();
}
