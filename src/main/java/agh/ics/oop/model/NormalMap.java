package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimal;
import agh.ics.oop.abstractions.AbstractMap;
import agh.ics.oop.interfaces.WorldElement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class NormalMap extends AbstractMap {

    private final int equatorYMin;
    private final int equatorYMax;

    public NormalMap(int width, int height) {
        this.width = width;
        this.height = height;
        int halfEquatorHeight = (int) Math.floor(height / 10.0);
        this.equatorYMin = (int) Math.floor(height /2.0) - halfEquatorHeight;
        this.equatorYMax = (int) Math.floor(height /2.0) + halfEquatorHeight;
    }
    @Override
    protected boolean spawnGrassPreferred() {
        LinkedList<Vector2d> spots = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = equatorYMin; j <= equatorYMax; j++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            grassSet.put(spots.get(0), new Grass(spots.get(0), 0) {
            });
            return true;
        } else {
            return false;
        }
    }



    @Override
    protected boolean spawnGrassNonPreferred() {
        LinkedList<Vector2d> spots = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < equatorYMin; j++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
            for (int j = equatorYMax + 1; j < height; j++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            grassSet.put(spots.get(0), new Grass(spots.get(0), 0));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void place(AbstractAnimal animal) {

    }

    @Override
    public void move(AbstractAnimal animal) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public List<WorldElement> getElements() {
        return null;
    }

    @Override
    public Boundary getCurrentBounds() {
        return null;
    }

    @Override
    public UUID getId() {
        return null;
    }
}
