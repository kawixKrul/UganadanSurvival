package agh.ics.oop.model;

import java.util.HashSet;
public abstract class AbstractMap {
    public int width;
    public int height;
    public HashSet<Vector2d> grassSet = new HashSet<>();



    private void spawnGrass() {
        boolean grassSpawned;
        if (Math.random() < 0.8) {
            grassSpawned = spawnGrassPreferred();
            if (!grassSpawned) spawnGrassNonPreferred();
        } else {
            grassSpawned = spawnGrassNonPreferred();
            if (!grassSpawned) spawnGrassPreferred();
        }
    }

    protected abstract boolean spawnGrassPreferred();

    protected abstract boolean spawnGrassNonPreferred();
}
