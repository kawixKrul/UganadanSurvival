package agh.ics.oop.abstractions;

import agh.ics.oop.interfaces.MapChangeListener;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.model.AllAnimals;
import agh.ics.oop.model.Vector2d;

import java.util.*;

public abstract class AbstractMap implements WorldMap {
    public int width;
    public int height;

    public HashMap<Vector2d, AbstractPlant> grassSet = new HashMap<>();

    public AllAnimals animals = new AllAnimals();

    public ArrayList<WorldElement> allobjects = new ArrayList<WorldElement>();
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

    public void spawnMultipleGrass(int howMuch){
        for(int i =0; i<howMuch;i++){
            this.spawnGrass();
        }
    }
    public void addAnimal(AbstractAnimal animal){
        animals.addAnimal(animal);
        mapChanged("Bydle");
    }

    public void removeAnimal(AbstractAnimal animal){
        animals.removeAnimal(animal);
        mapChanged("Bydle nieżyje");
    }
    public void whichAreDead(){
        animals.checkIfAllAnimalsAlive(currentday);
    }


    public void moveAnimals(){
        animals.moveAnimals(this);
    }

    public void eatGrass() {
        Iterator<Map.Entry<Vector2d,AbstractPlant>> it = grassSet.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry<Vector2d,AbstractPlant> next = it.next();
            if (animals.eatGrassAtPosition(next.getKey(), next.getValue()))
                mapChanged("Bydle zjadłoTrawe");
                it.remove();
        }
    }

    @Override
    public List<WorldElement> getElements() {
        allobjects.clear();
        animals.getAllAnimals().forEach(o -> allobjects.add((WorldElement) o));
        grassSet.values().forEach(g -> allobjects.add((WorldElement) g));
        return allobjects;
    }

    public void breedAnimals(int energyToBreed) {
        //mapChanged("bydle się rucha");
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
    public void passDay(int energyToBreed,int plantGrowthPerDay) {
        whichAreDead();
        moveAnimals();
        eatGrass();
        //breedAnimals(energyToBreed);
        spawnMultipleGrass(plantGrowthPerDay);
        currentday++;
    }
    @Override
    public void mapChanged(String message) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observers.forEach(o -> o.mapChanged(this, message));
    }

    @Override
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }
}
