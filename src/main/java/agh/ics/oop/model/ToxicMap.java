package agh.ics.oop.model;


import agh.ics.oop.abstractions.AbstractAnimalFactory;
import agh.ics.oop.abstractions.AbstractWorldMap;
import agh.ics.oop.enums.MapObstacle;

public class ToxicMap extends AbstractWorldMap {

    public ToxicMap(Boundary boundary,
                    int plantEnergy,
                    AbstractAnimalFactory animalFactory,
                    int requiredEnergyToReproduce,
                    int breedingConsumptionEnergy) {
        super(boundary, plantEnergy, animalFactory, requiredEnergyToReproduce, breedingConsumptionEnergy);
    }

    @Override
    public void spawnPlant() {
        Vector2d position = Vector2d.getRandomVector2d(boundary);
        plants.put(position, new ToxicPlant(position, plantEnergy));
        mapChanged("Spawned Toxic Plant");
    }

    @Override
    public MapObstacle canMoveTo(Vector2d position) {
        if (super.canMoveTo(position) == MapObstacle.NONE) {
            if (plants.containsKey(position)) {
                return MapObstacle.TOXIC_PLANT;
            }
            if (position.precedes(boundary.upperRight()) && position.follows(boundary.upperRight())) {
                return MapObstacle.NONE;
            }
            if (position.getX() == boundary.upperRight().getX() || position.getX() == boundary.lowerLeft().getX()) {
                return MapObstacle.LEFT_RIGHT_BOUND;
            }
            if (position.getY() == boundary.upperRight().getY() || position.getY() == boundary.lowerLeft().getY()) {
                return MapObstacle.TOP_BOTTOM_BOUND;
            }
        }
        return super.canMoveTo(position);
    }

}
