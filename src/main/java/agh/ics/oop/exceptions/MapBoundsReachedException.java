package agh.ics.oop.exceptions;


/**
 * exception for when animal reaches map bounds
 */
public class MapBoundsReachedException extends Exception {
    public MapBoundsReachedException() {
        super("Map bounds reached!");
    }
}
