package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractMap;

import java.util.Collections;
import java.util.LinkedList;

public class ToxicMap extends AbstractMap {
    private final int squareStartX;
    private final int squareStartY;
    private final int squareEndX;
    private final int squareEndY;

    private final int chanceOfContamination;
    public ToxicMap(int width, int height,int squaresize,int chanceOfContamination) {
        this.width = width;
        this.height = height;
        this.chanceOfContamination = chanceOfContamination;
        if (squaresize <= width && squaresize <=height ){
            this.squareStartY = (int) Math.floor(height /2.0) - (int) Math.floor(squaresize /2.0);
            this.squareEndY = (int) Math.floor(height /2.0) + (int) Math.floor(squaresize /2.0);
            this.squareStartX = (int) Math.floor(width /2.0) - (int) Math.floor(squaresize /2.0);
            this.squareEndX = (int) Math.floor(width /2.0) + (int) Math.floor(squaresize /2.0);
        }

        else {
            this.squareStartY = 0;
            this.squareEndY = height;
            this.squareStartX = 0;
            this.squareEndX = width;
        }
    }


    @Override
    protected boolean spawnGrassPreferred() {
        LinkedList<Vector2d> spots = new LinkedList<>();
        for (int i = squareStartX; i < squareEndX; i++) {
            for (int j = squareStartY; j < squareEndY; j++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            if (Math.random() < chanceOfContamination) {
                grassSet.put(spots.get(0), new ToxicPlant(spots.get(0)));
            }
            else{
                grassSet.put(spots.get(0), new Grass(spots.get(0)));
            }

            return true;
        } else {
            return false;
        }
    }



    @Override
    protected boolean spawnGrassNonPreferred() {
        LinkedList<Vector2d> spots = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < squareStartY; j++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
            for (int j = squareEndY; j < height; j++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
        }
        for (int j = squareStartY; j < squareEndY; j++) {
            for (int i = 0; i < squareStartX; i++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
            for (int i = squareEndX; i < width; i++) {
                Vector2d newSpot = new Vector2d(i, j);
                if (!grassSet.containsKey(newSpot)){
                    spots.add(new Vector2d(i, j));
                }
            }
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            grassSet.put(spots.get(0), new Grass(spots.get(0)));
            return true;
        } else {
            return false;
        }
    }
}
