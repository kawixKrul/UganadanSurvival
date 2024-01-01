package agh.ics.oop.abstractions;

import agh.ics.oop.enums.MapObstacle;
import agh.ics.oop.interfaces.MapChangeListener;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;

import java.util.*;
import java.util.stream.Stream;

abstract public class AbstractWorldMap implements WorldMap {
    protected final Boundary boundary;
    protected final int plantEnergy;
    private final AbstractAnimalFactory animalFactory;
    private final int requiredEnergyToReproduce;
    private final int breedingConsumptionEnergy;
    private final UUID id = UUID.randomUUID();
    protected final Map<Vector2d, AbstractPlant> plants = Collections.synchronizedMap(new HashMap<>());
    private final List<MapChangeListener> observers = new LinkedList<>();
    protected final Map<Vector2d, TreeSet<AbstractAnimal>> animals = Collections.synchronizedMap(new HashMap<>());
    private final List<AbstractAnimal> deadAnimals = Collections.synchronizedList(new LinkedList<>());

    public AbstractWorldMap(Boundary boundary,
                            int plantEnergy,
                            AbstractAnimalFactory animalFactory,
                            int requiredEnergyToReproduce,
                            int breedingConsumptionEnergy) {
        this.boundary = boundary;
        this.plantEnergy = plantEnergy;
        this.animalFactory = animalFactory;
        this.requiredEnergyToReproduce = requiredEnergyToReproduce;
        this.breedingConsumptionEnergy = breedingConsumptionEnergy;
    }


    public void spawnAnimals(int count) {
        for (int i = 0; i < count; ++i) {
            spawnAnimal();
        }
        mapChanged("Spawned " + count + " animals");
    }

    public void spawnPlants(int count) {
        for (int i = 0; i < count; ++i) {
            spawnPlant();
        }
        mapChanged("Spawned " + count + " plants");
    }

    public void spawnAnimal() {
        AbstractAnimal animal = animalFactory.create();
        place(animal);
    }

    public abstract void spawnPlant();

    @Override
    public void place(AbstractAnimal animal) {
        Vector2d position = animal.getPosition();
        animals.computeIfAbsent(position, k -> new TreeSet<>());
        animals.get(position).add(animal);
    }

    @Override
    public void move(AbstractAnimal animal) {
        Vector2d oldPosition = animal.getPosition();
        animals.get(oldPosition).remove(animal);
        animal.move(this);
        place(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        var set = animals.get(position);
        if (set != null && !set.isEmpty()) {
            return set.first();
        }
        return plants.get(position);
    }

    public List<AbstractAnimal> getAnimals() {
        return animals.values()
                .stream()
                .map(set -> set.stream().toList())
                .reduce(new LinkedList<>(), (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
    }

    @Override
    public List<WorldElement> getElements() {
        return Stream.concat(
                plants.values()
                    .stream()
                    .map(plant -> (WorldElement) plant),
                getAnimals()
                    .stream()
                    .map(animal -> (WorldElement) animal))
                .toList();
    }

    @Override
    public Boundary getCurrentBounds() {
        return boundary;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void mapChanged(String message) {
        this.observers.forEach(observer -> observer.mapChanged(this, message));
        //System.out.println(message);
    }

    @Override
    public void addObserver(MapChangeListener observer) {
        this.observers.add(observer);
    }

    public List<MapChangeListener> getObservers() {
        return observers;
    }

    @Override
    public List<AbstractAnimal> procreateAllAnimals() {
        List<AbstractAnimal> children = new LinkedList<>();
        for (TreeSet<AbstractAnimal> set : animals.values()) {
            if (set.size() > 1) {
                AbstractAnimal first = set.first();
                AbstractAnimal second = set.higher(first);
                while (second != null && first != null) {
                    if (first.canReproduce(second, requiredEnergyToReproduce)) {
                        AbstractAnimal child = animalFactory
                                .create(first.getPosition(), first.reproduce(second, breedingConsumptionEnergy));
                        children.add(child);
                        first.addChild(child);
                        second.addChild(child);
                    }
                    first = set.higher(second);
                    second = set.higher(first);
                }
            }
        }
        for (AbstractAnimal child : children) {
            place(child);
        }
        mapChanged("Spawned " + children.size() + " children");
        return children;
    }

    @Override
    public List<AbstractAnimal> removeDeadAnimals(int day) {
        List<AbstractAnimal> deaths = new LinkedList<>();
        for (TreeSet<AbstractAnimal> set : animals.values()) {
            for (AbstractAnimal animal : set) {
                if (!animal.checkIfAlive(day)) {
                    deaths.add(animal);
                }
            }
        }
        for (AbstractAnimal animal : deaths) {
            animals.get(animal.getPosition()).remove(animal);
            deadAnimals.add(animal);
        }
        mapChanged("Killed " + deaths.size() + " animals");
        return deaths;
    }

    @Override
    public void removeDeadAnimalsByAnimal(List<AbstractAnimal> deadAnimals) {
        for (AbstractAnimal animal : deadAnimals) {
            animals.get(animal.getPosition()).remove(animal);
        }
        mapChanged("Killed " + deadAnimals.size() + " animals");
    }

    @Override
    public void consumePlants() {
        for (TreeSet<AbstractAnimal> set : animals.values()) {
            if (!set.isEmpty()) {
                AbstractAnimal first = set.first();
                AbstractPlant plant = plants.get(first.getPosition());
                if (plant != null) {
                    first.eatGrass(plant);
                    plants.remove(first.getPosition());
                }
            }
        }
        mapChanged("Consumed plants");
    }

    @Override
    public boolean isSimulationEnd() {
        return animals.values().stream().allMatch(TreeSet::isEmpty);
    }

    @Override
    public MapObstacle canMoveTo(Vector2d position) {
        if (position.getX() < boundary.lowerLeft().getX() || position.getX() > boundary.upperRight().getX()) {
            return MapObstacle.LEFT_RIGHT_BOUND;
        }
        if (position.getY() < boundary.lowerLeft().getY() || position.getY() > boundary.upperRight().getY()) {
            return MapObstacle.TOP_BOTTOM_BOUND;
        }
        return MapObstacle.NONE;
    }
}
