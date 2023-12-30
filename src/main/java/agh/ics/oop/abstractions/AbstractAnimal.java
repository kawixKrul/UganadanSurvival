package agh.ics.oop.abstractions;

import agh.ics.oop.enums.MapDirection;
import agh.ics.oop.exceptions.MapBoundsReachedException;
import agh.ics.oop.exceptions.ToxicPlantSpottedException;
import agh.ics.oop.interfaces.MoveValidator;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.Vector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


public abstract class AbstractAnimal implements WorldElement, Comparable<AbstractAnimal> {
    private static final int REQUIRED_ENERGY_TO_MOVE = 5;
    private MapDirection orientation;
    private Vector2d position;
    protected final Genome genome;
    private int energy;
    private int age;
    private final List<AbstractAnimal> children;
    private int deathDay;
    private int grassConsumed;

    public AbstractAnimal(Vector2d initialPosition, int initialEnergy, Genome genome) {
        this.genome = genome;
        this.position = initialPosition;
        this.orientation = MapDirection.NORTH;
        this.energy = initialEnergy;
        this.age = 0;
        this.children = new LinkedList<>();
        this.deathDay = -1;
        this.grassConsumed = 0;
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
        var newPosition = this.position.add(this.orientation.toUnitVector());
        try {
            if (moveValidator.canMoveTo(newPosition)) {
                this.position = newPosition;
            }
        } catch (ToxicPlantSpottedException e) {
            if (Math.random() > 0.2) {
                this.position = newPosition;
            } else {
                this.position = this.position.add(this.orientation.toUnitVector().opposite());
            }
        } catch (MapBoundsReachedException e) {
            this.orientation = this.orientation.next(4);
        } finally {
            this.energy -= REQUIRED_ENERGY_TO_MOVE;
            age++;
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
        return Integer.compare(this.energy, other.energy);
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
