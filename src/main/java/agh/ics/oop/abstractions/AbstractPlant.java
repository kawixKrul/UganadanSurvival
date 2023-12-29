package agh.ics.oop.abstractions;

import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.model.Vector2d;

public abstract class AbstractPlant implements WorldElement {
    private final Vector2d position;

    public AbstractPlant(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    abstract public int getEnergy();
}
