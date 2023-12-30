package agh.ics.oop.interfaces;

/**
 * interface for map observers
 */

public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message);
}
