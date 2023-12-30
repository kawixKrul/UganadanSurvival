package agh.ics.oop.exceptions;

/**
 * exception for when animal tries to eat a toxic plant for the first time
 */
public class ToxicPlantSpottedException extends Exception {
    public ToxicPlantSpottedException() {
        super("Toxic plant spotted!");
    }
}
