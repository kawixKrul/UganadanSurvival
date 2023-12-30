package agh.ics.oop.abstractions;

import agh.ics.oop.interfaces.MapChangeListener;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.model.AllAnimals;
import agh.ics.oop.model.Vector2d;

import java.util.*;

public abstract class AbstractMap implements WorldMap {
    public int width;
    public int height;
    public HashMap<Vector2d, AbstractPlant> grassSet = new HashMap<>();

    public AllAnimals animals = new AllAnimals();

    public int currentday = 0;
    private List<MapChangeListener> observers = new LinkedList<>();


    public void spawnGrass() {
        boolean grassSpawned;
        if (Math.random() < 0.8) {
            grassSpawned = spawnGrassPreferred();
            if (!grassSpawned) spawnGrassNonPreferred();
        } else {
            grassSpawned = spawnGrassNonPreferred();
            if (!grassSpawned) spawnGrassPreferred();
        }
        mapChanged("trawka");
    }

    public void addAnimal(AbstractAnimal animal){
        animals.addAnimal(animal);
        mapChanged("Bydle");
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


    @Override
    public void mapChanged(String message) {
        observers.forEach(o -> o.mapChanged(this, message));
    }

    @Override
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }
}
