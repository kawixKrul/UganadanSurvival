package agh.ics.oop.model;

import agh.ics.oop.interfaces.WorldElement;

public class Grass implements WorldElement {
    final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}