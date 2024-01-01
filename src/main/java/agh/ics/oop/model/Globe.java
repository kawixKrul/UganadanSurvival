package agh.ics.oop.model;

import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.enums.MapObstacle;


public class Globe extends AbstractWorldMap {
    public Globe(Boundary boundary,
                 int plantEnergy,
                 AbstractAnimalFactory animalFactory,
                 int requiredEnergyToReproduce,
                 int breedingConsumptionEnergy) {
        super(boundary, plantEnergy, animalFactory, requiredEnergyToReproduce, breedingConsumptionEnergy);
    }

    @Override
    public void spawnPlant() {
        Vector2d position = Vector2d.getRandomVector2d(boundary);
        plants.put(position, new Grass(position, plantEnergy));
        mapChanged("Spawned Plant");
    }

    @Override
    public MapObstacle canMoveTo(Vector2d position) {
        if (position.getX() < boundary.lowerLeft().getX() || position.getX() > boundary.upperRight().getX()) {
            return MapObstacle.LEFT_RIGHT_BOUND;
        }
        if (position.getY() < boundary.lowerLeft().getY() || position.getY() > boundary.upperRight().getY()) {
            return MapObstacle.TOP_BOTTOM_BOUND;
        }
        return MapObstacle.NONE;
    }
}
