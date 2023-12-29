package agh.ics.oop.model;


import agh.ics.oop.abstractions.AbstractPlant;

public class Grass extends AbstractPlant {
    public static final int ENERGY = 10;

    public Grass(Vector2d position) {
        super(position);
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public int getEnergy() {
        return ENERGY;
    }
}