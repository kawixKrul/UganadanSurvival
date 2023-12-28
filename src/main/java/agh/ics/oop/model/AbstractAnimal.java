package agh.ics.oop.model;

import agh.ics.oop.enums.MapDirection;
import agh.ics.oop.exceptions.MapBoundsReachedException;
import agh.ics.oop.exceptions.ToxicPlantSpottedException;
import agh.ics.oop.interfaces.MoveValidator;
import agh.ics.oop.interfaces.WorldElement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractAnimal implements WorldElement, Comparable<AbstractAnimal> {
    public static final int REQUIRED_ENERGY_TO_PROCREATE = 40;
    public static final int REQUIRED_ENERGY_TO_SURVIVE = 0;
    public static final int REQUIRED_ENERGY_TO_MOVE = 10;
    private MapDirection orientation;
    private Vector2d position;
    private List<Integer> genome;
    private int energy;
    private int age;
    protected int geneActive;
    private final List<AbstractAnimal> children;
    private int deathDay;
    private int grassConsumed;

    /**
     * constructor for initializing animals at the start of the simulation
     */
    public AbstractAnimal(Vector2d initialPosition, int initialEnergy, int genomeLength) {
        this(initialPosition, initialEnergy);
        this.genome = IntStream.range(0, genomeLength)
                .mapToObj(i -> (int) (Math.random() * 8))
                .toList();
    }

    /**
     * constructor for initializing animals from procreation
     */
    public AbstractAnimal(Vector2d initialPosition, int initialEnergy, List<Integer> genome) {
        this(initialPosition, initialEnergy);
        this.genome = genome;
    }

    public AbstractAnimal(Vector2d initialPosition, int initialEnergy) {
        this.position = initialPosition;
        this.orientation = MapDirection.NORTH;
        this.energy = initialEnergy;
        this.age = 0;
        this.children = new LinkedList<>();
        this.deathDay = -1;
        this.grassConsumed = 0;
    }

    /**
     * standard procreation method with included genome mutation
     *
     * @param other other animal that is trying to procreate with this animal
     * @return corresponding subclass of AbstractAnimal if procreation was successful
     */

    public Optional<AbstractAnimal> procreate(AbstractAnimal other) {
        if (this.energy < REQUIRED_ENERGY_TO_PROCREATE || other.energy < REQUIRED_ENERGY_TO_PROCREATE) {
            return Optional.empty();
        }
        var strongerAnimal = this.energy > other.energy ? this : other;
        var weakerAnimal = this.energy > other.energy ? other : this;
        int genomeDistribution = strongerAnimal.energy/(strongerAnimal.energy+weakerAnimal.energy)*genome.size();
        var childGenome = mutateGenome(switch (Math.random() > 0.5 ? "stronger" : "weaker") {
            case "stronger" ->
                    Stream.concat(strongerAnimal.genome.subList(0, genomeDistribution).stream(),
                            weakerAnimal.genome.subList(genomeDistribution, genome.size()).stream())
                            .toList();
            case "weaker" ->
                    Stream.concat(weakerAnimal.genome.subList(0, genome.size()-genomeDistribution).stream(),
                            strongerAnimal.genome.subList(genome.size()-genomeDistribution, genome.size()).stream())
                            .toList();
            default ->
                    throw new IllegalStateException("Unexpected value: no bitches?");
        });
        this.energy -= REQUIRED_ENERGY_TO_PROCREATE;
        other.energy -= REQUIRED_ENERGY_TO_PROCREATE;
        var child = switch (this.getClass().getSimpleName()) {
            case "RegularAnimal" -> new RegularAnimal(this.position, REQUIRED_ENERGY_TO_PROCREATE*2, childGenome);
            case "CrazyAnimal" -> new CrazyAnimal(this.position, REQUIRED_ENERGY_TO_PROCREATE*2, childGenome);
            default -> throw new IllegalStateException("Unexpected value: " + this.getClass().getSimpleName());
        };
        this.children.add(child);
        other.children.add(child);
        return Optional.of(child);
    }

    private List<Integer> mutateGenome(List<Integer> genome) {
        if (Math.random() < 0.25) {
            int index = (int) (Math.random() * genome.size());
            genome.set(index, (int) (Math.random() * 8));
        }
        return genome;
    }


    /**
     * standard variant of move method for both map implementations
     * @param moveValidator move validator that checks if animal can move to the new position
     *                      found in map implementations
     */
    public void move(MoveValidator moveValidator) {
        this.orientation = this.orientation.next(this.genome.get(geneActive++ % this.genome.size()));
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
        return switch (this.energy < REQUIRED_ENERGY_TO_SURVIVE ? 0 : 1) {
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

    public List<Integer> getGenome() {
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
                ", geneActive=" + geneActive +
                ", children=" + children +
                ", deathDay=" + deathDay +
                ", grassConsumed=" + grassConsumed +
                ", allDescendants=" + getAllDescendantsNumber() +
                '}';
    }
}
