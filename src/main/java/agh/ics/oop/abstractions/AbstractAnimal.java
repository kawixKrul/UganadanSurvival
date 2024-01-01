package agh.ics.oop.abstractions;

import agh.ics.oop.enums.MapDirection;
import agh.ics.oop.interfaces.MoveValidator;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public abstract class AbstractAnimal implements WorldElement, Comparable<AbstractAnimal> {
    private static final int REQUIRED_ENERGY_TO_MOVE = 5;
    private static final AtomicInteger ID_CONSTRUCTOR = new AtomicInteger(0);
    private final int id = ID_CONSTRUCTOR.getAndIncrement();
    private MapDirection orientation = MapDirection.random();
    private Vector2d position;
    protected final Genome genome;
    private int energy;
    private int age = 0;
    private final List<AbstractAnimal> children = new LinkedList<>();
    private int deathDay = -1;
    private int grassConsumed = 0;

    public AbstractAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        this.genome = genome;
        this.position = initialPosition;
        this.energy = initialEnergy;
    }

    public boolean canReproduce(AbstractAnimal other, int requiredEnergy) {
        return this.energy >= requiredEnergy && other.energy >= requiredEnergy;
    }


    /**
     * creates list of genes for new animal based on parents genes
     * need to insert into factory of abstract animal to get gene mutations
     * @param other
     * @param energyConsumption energy consumed by each parents
     * @return list of genes for new animal
     */
    public List<Integer> reproduce(AbstractAnimal other, int energyConsumption) {
        var stronger = this.energy > other.energy ? this : other;
        var weaker = this.energy > other.energy ? other : this;
        var dominant = Math.random() > 0.5;
        int distribution = stronger.energy / (stronger.energy + weaker.energy)*genome.getGenomeLength();
        if (dominant) {
            stronger.energy -= energyConsumption;
            weaker.energy -= energyConsumption;
            return Stream.concat(
                    stronger.genome.getGenes()
                            .subList(0, distribution)
                            .stream(),
                    weaker.genome.getGenes()
                            .subList(distribution, genome.getGenomeLength())
                            .stream()
            ).toList();
        } else {
            stronger.energy -= energyConsumption;
            weaker.energy -= energyConsumption;
            return Stream.concat(
                    weaker.genome.getGenes()
                            .subList(0, genome.getGenomeLength() - distribution)
                            .stream(),
                    stronger.genome.getGenes()
                            .subList(genome.getGenomeLength() - distribution, genome.getGenomeLength())
                            .stream()
            ).toList();
        }
    }

    /**
     * standard variant of move method for both map implementations
     * catches exceptions bound to certain map types and modifies move behaviour based on them
     * @param moveValidator move validator that checks if animal can move to the new position
     *                      found in map implementations
     */
    public void move(MoveValidator moveValidator) {
        this.orientation = this.orientation.next(this.genome.getGeneAndMoveToNext());
        Vector2d predictedPosition = this.position.add(this.orientation.toUnitVector());
        switch (moveValidator.canMoveTo(predictedPosition)) {
            case NONE -> {
                this.position = predictedPosition;
                this.energy -= REQUIRED_ENERGY_TO_MOVE;
            }
            case TOXIC_PLANT -> {
                if (Math.random() < 0.2) {
                    this.position = this.position.add(MapDirection.random().toUnitVector());
                }
            }
            case TOP_BOTTOM_BOUND -> {
                this.orientation.next(4);
            }
            case LEFT_RIGHT_BOUND -> {
                if (predictedPosition.getX() < 0) {
                    this.position = new Vector2d(moveValidator.getCurrentBounds().upperRight().getX(), this.position.getY());
                } else {
                    this.position = new Vector2d(0, this.position.getY());
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + moveValidator.canMoveTo(predictedPosition));
        }
    }

    public void eatGrass(AbstractPlant plant) {
        this.energy += plant.getEnergy();
        this.grassConsumed++;
    }

    /**
     * checks if animal is alive, modifies its day of death
     *
     * @param day of the simulation
     * @return true if animal lives false otherwise
     */
    public boolean checkIfAlive(int day) {
        return switch (this.energy < 0 ? 0 : 1) {
            case 0 -> {
                this.deathDay = day;
                yield false;
            }
            case 1 -> true;
            default -> throw new IllegalStateException("jak rześki trzeba być, żeby nie żyć");
        };
    }

    @Override
    public int compareTo(AbstractAnimal other) {
        return Comparator.comparingInt(AbstractAnimal::getEnergy)
                .thenComparingInt(AbstractAnimal::getAge)
                .thenComparingInt(AbstractAnimal::getChildrenNumber)
                .thenComparingInt(AbstractAnimal::getId)
                .compare(this, other);

    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getChildrenNumber() {
        return children.size();
    }

    public int getAllDescendantsNumber() {
        return children.stream().mapToInt(AbstractAnimal::getAllDescendantsNumber).sum() + getChildrenNumber();
    }

    public Genome getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        this.age++;
    }

    public void addChild(AbstractAnimal child) {
        this.children.add(child);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "orientation=" + orientation +
                ", position=" + position +
                ", genome=" + genome +
                ", energy=" + energy +
                ", age=" + age +
                ", children=" + children +
                ", deathDay=" + deathDay +
                ", grassConsumed=" + grassConsumed +
                ", allDescendants=" + getAllDescendantsNumber() +
                '}';
    }
}
